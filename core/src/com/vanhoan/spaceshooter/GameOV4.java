package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOV4 implements Screen {

    private Skin skin;
    private Stage stage;

    private Image bg;
    private Button backButton;
    private Button playAgainButton;

    Sound clickSound;

    @Override
    public void show() {

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("asset/mainmenu.atlas"));

        bg = new Image(skin, "gameOver");
        backButton = new Button(skin, "back");
        playAgainButton = new Button(skin, "retry");

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonSound.wav"));

        backButton.setPosition(Gdx.graphics.getWidth()/4 - 80 , Gdx.graphics.getHeight()/4 -300f);
        playAgainButton.setPosition(Gdx.graphics.getWidth()/4 + 420f, Gdx.graphics.getHeight()/4 - 300f);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                clickSound.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level4Screen());
            }
        });

        bg.setWidth(1100f);
        bg.setHeight(2300f);
        backButton.setWidth(150f);
        backButton.setHeight(150f);
        playAgainButton.setWidth(150f);
        playAgainButton.setHeight(150f);

        stage.addActor(bg);
        stage.addActor(backButton);
        stage.addActor(playAgainButton);
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

    }

    @Override
    public void dispose() {

        skin.dispose();
        stage.dispose();

    }
}
