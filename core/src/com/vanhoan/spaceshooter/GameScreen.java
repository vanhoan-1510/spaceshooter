package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

//    private Texture

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
                    enemyShipTextureRegion, enemyShieldTextureRegion,
                    playerLaserTextureRegion, enemyLaserTextureRegion;

//    private Texture background;
    private TextureRegion[] backgrounds;
    private float backgroundHeight;



    //timing
//    private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private  float backgroundMaxScrollingSpeed;


    //world parameters
    private final int WORLD_WIDTH = 80;
    private final int WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;

    //game object
    private Ship playerShip, enemyShip;
    private LinkedList<Laser> playerLaserList, enemyLaserList;



    GameScreen() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//       background = new Texture("SpaceBackground1.jpg");
//       backgroundOffset = 0;

        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("SpaceBackground2");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float)(WORLD_HEIGHT) /4;
        backgroundHeight = WORLD_HEIGHT * 2;


        //initialize texture
        playerShipTextureRegion = textureAtlas.findRegion("playerShip2_green");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyRed3");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");

        //set up game objects
        playerShip = new PlayerShip(WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                80, 6,
                0.7f, 4, 45, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);

        enemyShip = new EnemyShip(WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 4,
                10, 10,
                2, 2,
                0.4f, 5, 40, 1.5f,
                enemyShipTextureRegion, enemyShieldTextureRegion ,enemyLaserTextureRegion);

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        batch = new SpriteBatch();

    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        detectInput(deltaTime);

        playerShip.update(deltaTime);
        enemyShip.update(deltaTime);

        //scrolling background
        renderBackground(deltaTime);

        //enemy ships
        enemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);

        //lasers
        renderLaser(deltaTime);

        //detect collisions between lasers and ship
        detectCollisions();

        //explosions
        renderExplosions(deltaTime);

        batch.end();
    }

    private void detectInput(float deltaTime){
        //keyboard input

        //strategy: determine the max distance  the ship can move
        //check it keys that matters  and move accordingly
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = - playerShip.boundingBox.x;
        downLimit = - playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT/2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0){
            playerShip.translate(Math.min(playerShip.movementSpeed * deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0){
            playerShip.translate(0f, Math.min(playerShip.movementSpeed * deltaTime, upLimit));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0){
            playerShip.translate(Math.max(-playerShip.movementSpeed * deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0){
            playerShip.translate(0f, Math.max(-playerShip.movementSpeed * deltaTime, downLimit));
        }

        //touch input
        if(Gdx.input.isTouched()){
            //get the screen position of the touch
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //convert to world position
            Vector2 touchPoint =  new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            //calculate the x and y differences
            Vector2 playerShipCentre = new Vector2(
                    playerShip.boundingBox.x + playerShip.boundingBox.width/2,
                    playerShip.boundingBox.y + playerShip.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD){
                float xTouchDifferent = touchPoint.x - playerShipCentre.x;
                float yTouchDifferent = touchPoint.y - playerShipCentre.y;

                //scale to the maximum speed of the ship
                float xMove = xTouchDifferent / touchDistance * playerShip .movementSpeed * deltaTime;
                float yMove = yTouchDifferent / touchDistance * playerShip .movementSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerShip.translate(xMove, yMove);
            }

        }

    }

    private void detectCollisions(){
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();

            if (enemyShip.intersects(laser.boundingBox)){
                //contact with enemy ship
                enemyShip.hit(laser);
                iterator.remove();
            }
        }

        //for enemy player laser, check whether it intersects an player ship
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();

            if (playerShip.intersects(laser.boundingBox)){
                //contact with player ship
                playerShip.hit(laser);
                iterator.remove();
            }
        }

    }

    private void renderExplosions(float deltaTime){

    }

    private void renderLaser(float deltaTime){
        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLasers();
            for (Laser laser: lasers) {
                playerLaserList.add(laser);
            }
        }
        //enemy lasers
        if (enemyShip.canFireLaser()) {
            Laser[] lasers = enemyShip.fireLasers();
            for (Laser laser: lasers) {
                enemyLaserList.add(laser);
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed*deltaTime;
            if (laser.boundingBox.x > WORLD_HEIGHT) {
                iterator.remove();
            }
        }
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed*deltaTime;
            if (laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime) {

        //update position of background images
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
                    WORLD_WIDTH, backgroundHeight);
        }
    }


    @Override
    public void resize(int width, int height) {
    viewport.update(width, height,true);
    batch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
    @Override
    public void dispose() {

    }
}
