package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class LoadingScreen implements Screen {

    SpriteBatch batch;
    Texture img;

    AssetManager assetManager;
    Texture loadingBarBackground;
    Texture loadingBarProgress;

    TextureRegion loadingBarProgressStart;
    TextureRegion loadingBarProgressBody;
    TextureRegion loadingBarProgressEnd;




    public void loadAsset(){

        // load synchronously
        assetManager.load("loading/badlogic.jpg", Texture.class);
        assetManager.load("loading/loading.png", Texture.class);
        assetManager.load("loading/loadingbar.png", Texture.class);
//        assetManager.load("Arcon.ttf", BitmapFont.class);
        assetManager.finishLoading();

        // load asynchronously
        assetManager.load("loading/1.jpg", Texture.class);
        assetManager.load("loading/2.jpg", Texture.class);
        assetManager.load("loading/3.jpg", Texture.class);
        assetManager.load("loading/4.jpg", Texture.class);
        assetManager.load("loading/5.jpg", Texture.class);
        assetManager.load("loading/6.jpg", Texture.class);
        assetManager.load("loading/7.jpg", Texture.class);
        assetManager.load("loading/8.jpg", Texture.class);
        assetManager.load("loading/9.jpg", Texture.class);
        assetManager.load("loading/a1.jpg", Texture.class);
        assetManager.load("loading/a2.jpg", Texture.class);
        assetManager.load("loading/a3.jpg", Texture.class);
        assetManager.load("loading/a4.jpg", Texture.class);
        assetManager.load("loading/a5.jpg", Texture.class);
        assetManager.load("loading/a6.jpg", Texture.class);
        assetManager.load("loading/a7.jpg", Texture.class);
        assetManager.load("loading/a8.jpg", Texture.class);
        assetManager.load("loading/a9.jpg", Texture.class);


    }

    public void LoadProcess(){

        batch = new SpriteBatch();
        assetManager = new AssetManager();
        this.loadAsset();
        img = assetManager.get("badlogic.jpg", Texture.class);
        loadingBarBackground = assetManager.get("loading.png", Texture.class);
        loadingBarProgress = assetManager.get("loadingbar.png",Texture.class);
//        font = assetManager.get("Arcon.ttf", BitmapFont.class);

        // bar width 489px = Start 20px Body 449px End 20px
        loadingBarProgressStart = new TextureRegion(loadingBarProgress, 0, 0, 20, loadingBarProgress.getHeight());
        loadingBarProgressBody = new TextureRegion(loadingBarProgress, 20, 0,500, loadingBarProgress.getHeight());
        loadingBarProgressEnd = new TextureRegion(loadingBarProgress, 1000, 0, 20,loadingBarProgress.getHeight());

    }

    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {

        if (assetManager.update())
            batch.draw(img, 0, 0);


        int initialPosX = 70;
        int initialPosY = 300;

        batch.draw(loadingBarBackground,
                initialPosX,
                initialPosY);

        batch.draw(loadingBarProgressStart,
                initialPosX,
                initialPosY);
        batch.draw(loadingBarProgressBody,
                initialPosX+loadingBarProgressStart.getRegionWidth(),
                initialPosY,
                loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),
                loadingBarProgressBody.getRegionHeight());
        batch.draw(loadingBarProgressEnd,
                initialPosX+loadingBarProgressStart.getRegionWidth()+loadingBarProgressBody.getRegionWidth()*assetManager.getProgress(),
                initialPosY);
        loadAsset();
//
//        font.draw(batch, "Loading..."+(int)(assetManager.getProgress()*100)+"%",
//                initialPosX + 170,
//                initialPosY + 38);
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
