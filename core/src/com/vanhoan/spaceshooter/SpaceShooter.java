package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class SpaceShooter extends Game {

	GameScreen gameScreen;
	SplashScreen splashScreen;
	LoadingScreen loadingScreen;
	public SpriteBatch batch;

	int gameState = 0;
	public static Random random = new Random();

	@Override
	public void create() {

//		batch = new SpriteBatch();
//		this.setScreen(new GameScreen());

//		GameOverScreen gameOverScreen = new GameOverScreen();
//		setScreen(gameOverScreen);

//		MenuScreen menuScreen = new MenuScreen();
//		setScreen(menuScreen);

		CompleteScreen completeScreen = new CompleteScreen();
		setScreen(completeScreen);

//		LevelScreen levelScreen = new LevelScreen();
//		setScreen(levelScreen);

//		Level2 level2 = new Level2();
//		setScreen(level2);
//
//		splashScreen = new SplashScreen();
//		setScreen(splashScreen);
//
//		gameScreen = new GameScreen();
//		setScreen(gameScreen);
	}


	@Override
	public void dispose() {
//		firstScreen.dispose();
//		gameScreen.dispose();
//		loadingScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
//

//		gameScreen.resize(width, height);
//		loadingScreen.resize(width,height);

	}
}