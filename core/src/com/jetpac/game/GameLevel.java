package com.jetpac.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameLevel {
	protected Music music;
	protected Texture background;
	protected TextureAtlas atlas;
	protected String[] ground;
	
	public GameLevel()
	{
		ground = new String[]{"groundl", "ground0", "ground1", "ground2", "ground3", "ground4", "ground5", "ground6", "ground7", "ground8", "ground9", "groundr"};
	}
	
	public void draw(Batch batch, float delta){
		batch.draw(background, 0, 0);
	}
	
	public void playMusic()
	{
		music.play();
	}
	
	public void stopMusic()
	{
		music.stop();
	}
	
	public void Initialize()
	{
		
	}
	
	public void dispose()
	{
		music.stop();
		music.dispose();
		atlas.dispose();
		background.dispose();
	}
}
