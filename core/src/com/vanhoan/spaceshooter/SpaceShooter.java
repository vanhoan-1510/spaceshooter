package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;

import java.util.Random;

public class SpaceShooter extends Game {
	public static Random random = new Random();

	@Override
	public void create() {

		SplashScreen splashScreen = new SplashScreen();
		setScreen(splashScreen);
	}


	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}
}