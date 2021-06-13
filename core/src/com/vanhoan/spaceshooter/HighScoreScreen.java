package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

import java.util.Locale;


public class HighScoreScreen implements Screen {

    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    BitmapFont scoreFont;
    private SpriteBatch batch;
    GameScreen gameScreen;
//    int scores = 0;

    private void prepareHUD(){
        //create Bitmap font for our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;fontParameter.borderWidth = 3.36f;
        fontParameter.color = new Color(1,1,1,0.3f);
        fontParameter.borderColor = new Color(1,1,1,0.3f);
        scoreFont= fontGenerator.generateFont(fontParameter);

        //scale the font to fit word
        scoreFont.getData().setScale(0.08f);

        //cal huh margin
        hudVerticalMargin = 50;
        hudLeftX = hudVerticalMargin;
        hudRightX = 20;
        hudCentreX = 20;
        hudRow1Y = 20;
        hudRow2Y = 20;
        hudSectionWidth = 20;
    }

    private void updateAndRenderHUD(){
        scoreFont.draw(batch,"Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        scoreFont.draw(batch,"Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);


        scoreFont.draw(batch, String.format(Locale.getDefault(), "%06d", gameScreen.scores), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
//        scoreFont.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
