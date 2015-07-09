package com.jetpac.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.jetpac.game.levels.Level1;

public class MarsJetPac extends Game {
	SpriteBatch batch;
	BitmapFont font;
	GameLevel[] levels;
	int currentLevelIndex;
	GameLevel currentLevel;
	MainMenuScreen menuScreen;
	GameScreen gameScreen;

	public void create() {
		Bullet.init();
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		currentLevelIndex = -1;
		levels = new GameLevel[]{new Level1()};
		menuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		this.setScreen(menuScreen);
	}

	public void dispose() {
		for(GameLevel l : levels)
		{
			l.dispose();
		}
		gameScreen.dispose();
		menuScreen.dispose();
		batch.dispose();
		font.dispose();
		super.dispose();
	}
	
	public GameLevel getLevel()
	{
		if(currentLevel == null)
			currentLevel = getNextLevel();
		return currentLevel;
	}
	
	public GameLevel getNextLevel()
	{
		if(currentLevel != null)
			currentLevel.dispose();
		currentLevelIndex++;
		if(currentLevelIndex > levels.length)
		{
			currentLevel = null;
			return null;
		}
		currentLevel = levels[currentLevelIndex];
		return currentLevel;
	}
	
	public void showMenu()
	{
		gameScreen.pause();
		menuScreen.resume();
		setScreen(menuScreen);
	}
	
	public void showGame()
	{
		menuScreen.pause();
		gameScreen.resume();
		setScreen(gameScreen);
	}
}
