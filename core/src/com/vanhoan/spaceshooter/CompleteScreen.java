package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;

public class CompleteScreen implements Screen {

    private Skin skin;
    private Stage stage;

    private Image bg;
    private Button backButton;
    private Button playAgainButton;
    private Button nextLevel;


    @Override
    public void show() {

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("mainmenu.json"), new TextureAtlas("asset/mainmenu.atlas"));

        bg = new Image(skin, "complete");
        backButton = new Button(skin, "back");
        playAgainButton = new Button(skin, "retry");
        nextLevel = new Button(skin, "btn_NextLevel");


        backButton.setPosition(Gdx.graphics.getWidth()/4 - 80 , Gdx.graphics.getHeight()/4 -300f);
        playAgainButton.setPosition(Gdx.graphics.getWidth()/4 + 420f, Gdx.graphics.getHeight()/4 - 300f);
        nextLevel.setPosition(Gdx.graphics.getWidth()/4 + 60f, Gdx.graphics.getHeight()/4 );

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        bg.setWidth(1100f);
        bg.setHeight(2300f);
        backButton.setWidth(150f);
        backButton.setHeight(150f);
        playAgainButton.setWidth(150f);
        playAgainButton.setHeight(150f);
        nextLevel.setWidth(400f);
        nextLevel.setHeight(150f);


        stage.addActor(bg);
        stage.addActor(backButton);
        stage.addActor(playAgainButton);
        stage.addActor(nextLevel);
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
