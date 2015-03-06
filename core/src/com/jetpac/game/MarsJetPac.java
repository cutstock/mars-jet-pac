package com.jetpac.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jetpac.game.levels.Level1;

public class MarsJetPac extends Game {
	SpriteBatch batch;
	BitmapFont font;
	GameLevel[] levels;
	int currentLevelIndex;
	GameLevel currentLevel;

	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		currentLevelIndex = -1;
		levels = new GameLevel[]{new Level1()};
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		super.dispose();
	}
	
	//текущий уровень
	public GameLevel getLevel()
	{
		if(currentLevel == null)
			currentLevel = getNextLevel();
		return currentLevel;
	}
	
	//следующий уровень
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
		if(currentLevel != null)
			currentLevel.Initialize();
		return currentLevel;
	}
}
