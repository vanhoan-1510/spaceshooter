package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MenuScreen implements Screen {

    private Skin skin;
    private Stage stage;

    private Image bg;
    private Button startButton;
    private Button optionButton;
    private Button levelButton;
    private Button highScoreButton;
    private Button exitButton;
    private Button facebookButton;

    Sound clickSound;
    Sound touchSound;
    Music menuSound;

    @Override
    public void show() {

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("asset/mainmenu.atlas"));

        bg = new Image(skin, "bg");
        startButton = new Button(skin, "btn_Start");
        optionButton = new Button(skin, "btn_Option");
        levelButton = new Button(skin, "btn_Levels");
        highScoreButton = new Button(skin, "btn_HighScore");
        exitButton = new Button(skin, "btn_Exit");
        facebookButton = new Button(skin, "facebook");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonSound.wav"));
        touchSound = Gdx.audio.newSound(Gdx.files.internal("sound/touchSound.wav"));
        menuSound = Gdx.audio.newMusic(Gdx.files.internal("sound/menuSound.mp3"));

//        bg.setPosition(-200f,-300f);
        startButton.setPosition(Gdx.graphics.getWidth()/4 + 40 , Gdx.graphics.getHeight()/4 + 650f);
        levelButton.setPosition(Gdx.graphics.getWidth()/4 + 40, Gdx.graphics.getHeight()/4 + 500f);
        optionButton.setPosition(Gdx.graphics.getWidth()/4 + 40, Gdx.graphics.getHeight()/4 + 350f);
        highScoreButton.setPosition(Gdx.graphics.getWidth()/4 + 40, Gdx.graphics.getHeight()/4 + 200f);
        exitButton.setPosition(Gdx.graphics.getWidth()/4 + 40, Gdx.graphics.getHeight()/4 + 50f);
        facebookButton.setPosition(Gdx.graphics.getWidth()/4 -200, Gdx.graphics.getHeight()/4 - 500);



        //set the size button and background
        bg.setWidth(1100f);
        bg.setHeight(2300f);
        startButton.setWidth(500);
        startButton.setHeight(100);
        levelButton.setWidth(500);
        levelButton.setHeight(100);
        optionButton.setWidth(500);
        optionButton.setHeight(100);
        highScoreButton.setWidth(500);
        highScoreButton.setHeight(100);
        exitButton.setWidth(500);
        exitButton.setHeight(100);
        facebookButton.setWidth(150);
        facebookButton.setHeight(150);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        levelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                System.exit(0);
            }
        });

        facebookButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                Gdx.net.openURI("https://www.facebook.com/griezmanprovip01020");
            }
        });

        stage.addActor(bg);
        stage.addActor(startButton);
        stage.addActor(optionButton);
        stage.addActor(levelButton);
        stage.addActor(highScoreButton);
        stage.addActor(exitButton);
        stage.addActor(facebookButton);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        menuSound.play();
        menuSound.setLooping(true);
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
        menuSound.dispose();
    }
}
