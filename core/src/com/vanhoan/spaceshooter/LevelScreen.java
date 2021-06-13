package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class LevelScreen implements Screen {

    private Skin skin;
    private Stage stage;

    private Image bg;
    private Button levelOne;
    private Button levelTwo;
    private Button levelThree;
    private Button levelFour;
    private Button levelFive;
    private Button backButton;

    @Override
    public void show() {

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("asset/mainmenu.atlas"));

        bg = new Image(skin, "bg2");
        levelOne = new Button(skin, "btn1");
        levelTwo = new Button(skin, "btn2");
        levelThree = new Button(skin, "btn3");
        levelFour = new Button(skin, "btn4");
        levelFive = new Button(skin, "btn5");
        backButton = new Button(skin, "back");

        bg.setPosition(-10f,0f);
        levelOne.setPosition(Gdx.graphics.getWidth()/4 -80f, Gdx.graphics.getHeight()/4 +500f);
        levelTwo.setPosition(Gdx.graphics.getWidth()/4 +65f, Gdx.graphics.getHeight()/4 + 500f);
        levelThree.setPosition(Gdx.graphics.getWidth()/4 + 210f, Gdx.graphics.getHeight()/4 + 500f);
        levelFour.setPosition(Gdx.graphics.getWidth()/4 + 355f, Gdx.graphics.getHeight()/4 + 500f);
        levelFive.setPosition(Gdx.graphics.getWidth()/4 + 500f, Gdx.graphics.getHeight()/4 + 500f);
        backButton.setPosition(Gdx.graphics.getWidth()/4 + 210f, Gdx.graphics.getHeight()/4 + 250f);


        bg.setWidth(1100);
        bg.setHeight(2300);
        levelOne.setWidth(110f);
        levelOne.setHeight(110f);
        levelTwo.setWidth(110f);
        levelTwo.setHeight(110f);
        levelThree.setWidth(110f);
        levelThree.setHeight(110f);
        levelFour.setWidth(110f);
        levelFour.setHeight(110f);
        levelFive.setWidth(110f);
        levelFive.setHeight(110f);

        levelOne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

//        levelTwo.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y){
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2());
//            }
//        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        stage.addActor(bg);
        stage.addActor(levelOne);
        stage.addActor(levelTwo);
        stage.addActor(levelThree);
        stage.addActor(levelFour);
        stage.addActor(levelFive);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}