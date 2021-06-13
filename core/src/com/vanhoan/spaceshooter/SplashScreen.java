package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class SplashScreen implements Screen {

    private Texture texture = new Texture(Gdx.files.internal("asset/mainmenu1.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();

    Music splashSound;

    @Override
    public void show() {

        splashImage.setHeight(2300f);
        splashImage.setWidth(1100f);

        splashSound = Gdx.audio.newMusic(Gdx.files.internal("sound/splashSound.mp3"));

        stage.addActor(splashImage);
        splashSound.play();
        splashImage.addAction(Actions.sequence(Actions.alpha(0),
                                                Actions.fadeIn(4.0f),
                                                Actions.delay(1f),
                                                Actions.run(new Runnable() {
        @Override
        public void run() {

            ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        })));

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
        texture.dispose();
        stage.dispose();
    }
}
