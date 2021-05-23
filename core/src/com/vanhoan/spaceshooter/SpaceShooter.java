package com.vanhoan.spaceshooter;

import com.badlogic.gdx.Game;

public class SpaceShooter extends Game {

	GameScreen gameScreen;

	@Override
	public void create() {

		gameScreen = new GameScreen();
		setScreen(gameScreen);

	}


	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width, height);
	}
}
