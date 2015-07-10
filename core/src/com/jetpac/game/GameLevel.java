package com.jetpac.game;

import java.util.ArrayList;

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
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected int enemiesLiveCount;
	protected int enemiesCount;
	protected World world;
	protected float enemyDeltaTime = 0;
		
	public void draw(Batch batch, float delta) {
		batch.draw(background, 0, 0);
	}
	
	public void playMusic()	{
		if(!music.isPlaying())
			music.play();
	}
	
	public void stopMusic()	{
		if(music.isPlaying())
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
	
	public abstract Enemy createEnemy(World world);
	
	public abstract void act(float delta);
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
}
