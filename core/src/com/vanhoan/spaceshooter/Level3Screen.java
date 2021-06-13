package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class Level3Screen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;
    private Texture gameOver;

//    private Texture

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion,
            bossTextureRegion;

    //    private Texture background;
    private TextureRegion[] backgrounds;
    private float backgroundHeight;

//    private float lives = 3f;


    //timing
//    private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private  float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawn = 1f;
    private float enemySpawnTimer = 0f;
    int count = 0;
    int count1 = 0;


    //world parameters
    private final float WORLD_WIDTH = 100;
    private final float WORLD_HEIGHT = 200;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;

    //game object
    private PlayerShip playerShip;
    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Laser> playerLaserList, enemyLaserList, bossLaserList;
    private LinkedList<Explosion> explosionList;


    private LinkedList<Boss> bossList;
    private float timeBetweenBossSpawn = 1f;
    private float bossSpawnTimer = 0f;


    public int scores = 0;

    //Head up display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    //sound
    Sound defeat;
    Sound laserSound;
    Music gameOverSound;
    Sound completeSound;
    Sound explosion;
    Music bgMusic;

    Level3Screen() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

//       background = new Texture("SpaceBackground1.jpg");
//       backgroundOffset = 0;

        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("background");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float)(WORLD_HEIGHT) /4;
        backgroundHeight = WORLD_HEIGHT * 2;


        //initialize texture
        playerShipTextureRegion = textureAtlas.findRegion("playerShip");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyShip");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield1");
        enemyShieldTextureRegion.flip(false, true);
        playerLaserTextureRegion = textureAtlas.findRegion("player_laser");
        enemyLaserTextureRegion = textureAtlas.findRegion("enemy_laser");
        bossTextureRegion = textureAtlas.findRegion("enemy_boss");


        //explosion
        explosionTexture  = new Texture("explosion.png");

        gameOver = new Texture("complete1.png");

        //sound
        defeat = Gdx.audio.newSound(Gdx.files.internal("sound/clickSound.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sound/laserSound.mp3"));
        gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("sound/gameOverSound.wav"));
        completeSound = Gdx.audio.newSound(Gdx.files.internal("sound/completeSound.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/bgMusic.mp3"));

        //set up game objects
        playerShip = new PlayerShip(WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                100, 1,
                0.7f, 4, 70, 0.4f,
                playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion);



        //enemy
        enemyShipList = new LinkedList<EnemyShip>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();


        //boss

        bossList = new LinkedList<Boss>();;
        bossLaserList = new LinkedList<>();

        batch = new SpriteBatch();

        prepareHUD();
    }



    private void prepareHUD(){
        //create Bitmap font for our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 60;fontParameter.borderWidth = 2.6f;
        fontParameter.color = new Color(1,1,1,0.3f);
        fontParameter.borderColor = new Color(1,1,1,0.3f);
        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit word
        font.getData().setScale(0.08f);

        //cal huh margin
        hudVerticalMargin = font.getCapHeight()/2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3  - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }

    @Override
    public void render(float deltaTime) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bgMusic.play();
        bgMusic.setLooping(true);
        //scrolling background
        renderBackground(deltaTime);



        detectInput(deltaTime);
        playerShip.update(deltaTime);

        if (count <= 39) {
            spawnEnemyShip(deltaTime);
        }
//            spawnBoss(deltaTime);

        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip, deltaTime);
            enemyShip.update(deltaTime);

            //enemy ships
            enemyShip.draw(batch);
        }


        //player ship
        playerShip.draw(batch);

        if (scores == 400 && count1 < 1){
            spawnBoss(deltaTime);
        }

        ListIterator<Boss> bossListIterator = bossList.listIterator();
        while (bossListIterator.hasNext()) {
            Boss boss = bossListIterator.next();
            moveBoss(boss, deltaTime);
            boss.update(deltaTime);

            //enemy ships
            boss.draw(batch);
        }

        //lasers
        renderLaser(deltaTime);

        //detect collisions between lasers and ship
        detectCollisions();

        if (playerShip.lives <= 0){
            this.dispose();
            gameOverSound.play();
            gameOverSound.setVolume(0.5f);
            ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOV3());
        }

        if (scores  >= 800){
            this.dispose();
            completeSound.play(0.5f);
            ((Game)Gdx.app.getApplicationListener()).setScreen(new CPL3());
        }

        //explosions
        updateAndRenderExplosions(deltaTime);

        //hub rending
        updateAndRenderHUD();

        batch.end();
    }

    private void updateAndRenderHUD(){
        font.draw(batch,"Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch,"Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);


        font.draw(batch, String.format(Locale.getDefault(), "%06d", scores), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);

    }

    public void gameOver(){
        batch.draw(gameOver,Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
    }
    public void spawnEnemyShip(float deltaTime){

        enemySpawnTimer += deltaTime;

        while (enemySpawnTimer > timeBetweenEnemySpawn) {
            enemyShipList.add(new EnemyShip(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT - 5,
                    10, 10,
                    70, 3,
                    0.7f, 5, 40, 2f,
                    enemyShipTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawn;
            count ++;
        }

    }

    public void spawnBoss(float deltaTime) {

        bossSpawnTimer += deltaTime;
        if (bossSpawnTimer > timeBetweenBossSpawn) {
            bossList.add(new Boss(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT - 5,
                    30, 30,
                    100, 80,
                    2f, 5, 100, 0.2f,
                    bossTextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion));
            bossSpawnTimer -= timeBetweenBossSpawn;
            count1++;
        }
    }


    private void detectInput(float deltaTime){
        //keyboard input

        //strategy: determine the max distance  the ship can move
        //check it keys that matters  and move accordingly
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = - playerShip.boundingBox.x;
        downLimit = - playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float)WORLD_HEIGHT/2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

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
                    playerShip.boundingBox.y + playerShip.boundingBox.height-20);

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

    private void moveEnemy(EnemyShip enemyShip, float deltaTime) {
        //strategy: determine the max distance the ship can move

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = (float)WORLD_HEIGHT/2-enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove,leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove,downLimit);

        enemyShip.translate(xMove,yMove);
    }

    private void moveBoss(Boss boss, float deltaTime) {
        //strategy: determine the max distance the ship can move

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -boss.boundingBox.x;
        downLimit = (float)WORLD_HEIGHT/2-boss.boundingBox.y;
        rightLimit = WORLD_WIDTH - boss.boundingBox.x - boss.boundingBox.width;
        upLimit = WORLD_HEIGHT - boss.boundingBox.y - boss.boundingBox.height;

        float xMove = boss.getDirectionVector().x * boss.movementSpeed * deltaTime;
        float yMove = boss.getDirectionVector().y * boss.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove,leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove,downLimit);

        boss.translate(xMove,yMove);
    }


    private void detectCollisions() {
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.boundingBox)) {
                    //contact with enemy ship
                    if(enemyShip.hitAndCheckDestroy(laser)){
                        enemyShipListIterator.remove();
                        explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));
                        scores += 10;
                        defeat.play();
                    };
                    laserListIterator.remove();
                    break;
                }
            }
        }

        //boss

        laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Boss> bossListIterator = bossList.listIterator();
            while (bossListIterator.hasNext()) {
                Boss boss = bossListIterator.next();

                if (boss.intersects(laser.boundingBox)) {
                    //contact with enemy ship
                    if(boss.hitAndCheckDestroy(laser)){
                        bossListIterator.remove();
                        explosionList.add(new Explosion(explosionTexture, new Rectangle(boss.boundingBox), 2f));
                        scores += 400;
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }

        //for each enemy laser, check whether it intersects the player ship
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)) {
                //contact with player ship
                if(playerShip.hitAndCheckDestroy(laser)){

                    explosionList.add(new Explosion(explosionTexture, new Rectangle(playerShip.boundingBox),
                            1f));
                    playerShip.lives -= 1;
                    explosion.play();

                }
                laserListIterator.remove();
            }
        }

        laserListIterator = bossLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)) {
                //contact with player ship
                if(playerShip.hitAndCheckDestroy(laser)){

                    explosionList.add(new Explosion(explosionTexture, new Rectangle(playerShip.boundingBox),
                            1f));
                    playerShip.lives -= 1;
                    explosion.play();

                }
                laserListIterator.remove();
            }
        }

    }

    private void updateAndRenderExplosions(float deltaTime){

        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while(explosionListIterator.hasNext()){
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()){
                explosionListIterator.remove();
            }
            else{
                explosion.draw(batch);
            }
        }

    }

    private void renderLaser(float deltaTime) {
        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLasers();
            for (Laser laser : lasers) {
                playerLaserList.add(laser);
                laserSound.play(0.1f);

            }
        }
        //enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                for (Laser laser : lasers) {
                    enemyLaserList.add(laser);
                }
            }
        }

        //boss lasers
        ListIterator<Boss> bossListIterator = bossList.listIterator();
        while (bossListIterator.hasNext()) {
            Boss boss = bossListIterator.next();
            if (boss.canFireLaser()) {
                Laser[] lasers = boss.fireLasers();
                for (Laser laser : lasers) {
                    bossLaserList.add(laser);
                }
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if (laser.boundingBox.x > WORLD_HEIGHT) {
                iterator.remove();
            }
        }
        iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }

        iterator = bossLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
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
        bgMusic.dispose();
    }
}
