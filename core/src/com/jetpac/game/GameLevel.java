package com.jetpac.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameLevel {
	protected Music music;
	protected Texture background;
	protected TextureAtlas groundAtlas;
	protected TextureAtlas enemyAtlas;
	protected String[] ground;
	
	public GameLevel() {
		ground = new String[]{"groundl", "ground0", "ground1", "ground2", "ground3", "ground4", "ground5", "ground6", "ground7", "ground8", "ground9", "groundr"};
	}
	
	public void draw(Batch batch, float delta) {
		batch.draw(background, 0, 0);
	}
	
	public void playMusic()	{
		music.play();
	}
	
	public void stopMusic()	{
		music.stop();
	}
	
	public void Initialize(World world)	{
	}
	
	public void dispose() {
		if(music != null)
		{
			music.stop();
			music.dispose();
		}
		if(groundAtlas != null)
			groundAtlas.dispose();
		if(enemyAtlas != null)
			enemyAtlas.dispose();
		if(background != null)
			background.dispose();
	}
}
