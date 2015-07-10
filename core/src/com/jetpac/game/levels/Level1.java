package com.jetpac.game.levels;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jetpac.game.Enemy;
import com.jetpac.game.GameLevel;

public class Level1 extends GameLevel {
	
	private int blockSize = 32;
	private int blocksCount;
	private List<String> blocks;
	private List<String> skyBlocks;
	protected String[] groundMap = new String[]{"groundl", "ground0", "ground1", "ground2", "ground3", "ground4", "ground5", "ground6", "ground7", "ground8", "ground9", "groundr"};
	protected String[] enemyType = new String[]{"green", "purple", "white", "black", "yellow", "red", "orange", "blue"};
	
	@Override
	public void Initialize(World world)
	{
		super.Initialize(world);
		enemiesCount = 100;
		enemiesLiveCount = 10;
		this.world = world;
		groundAtlas = new TextureAtlas("ground_brown.pack");
		enemyAtlas = new TextureAtlas("bubbles.pack");
		background = new Texture(Gdx.files.internal("background0.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("game-game_0.ogg"));
		music.setLooping(true);
		blocks = new ArrayList<String>();
		blocksCount = Gdx.graphics.getWidth()/blockSize;
		
		blocks.add(groundMap[2]);
		for(int i = 0; i < blocksCount - 2; i++){
			blocks.add(groundMap[3]);
		}
		blocks.add(groundMap[4]);
		
		skyBlocks = new ArrayList<String>();
		skyBlocks.add(groundMap[2]);
		for(int i = 0; i < Gdx.graphics.getWidth()/blockSize/3 - 2; i++){
			skyBlocks.add(groundMap[3]);
		}
		skyBlocks.add(groundMap[4]);
		
		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(Gdx.graphics.getWidth(), blockSize); //a 2x2 rectangle
		
		//fixture definition
	    FixtureDef floorFixtureDef = new FixtureDef();
	    floorFixtureDef.shape = floorShape;
	    floorFixtureDef.density = 1.0f;
		
		BodyDef floorBodyDef = new BodyDef();
		floorBodyDef.type = BodyDef.BodyType.StaticBody;
		floorBodyDef.position.set(0.0f, 0.0f);
		
		world.createBody(floorBodyDef).createFixture(floorFixtureDef);
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		// TODO Auto-generated method stub
		super.draw(batch, delta);
		float pos = 0;
		for(String b : blocks){
			Sprite block = new Sprite(groundAtlas.findRegion(b));
			block.setSize(blockSize, blockSize);
			block.setX(pos);
			pos += block.getWidth();
			block.draw(batch);
		}
		
		pos = Gdx.graphics.getWidth() * 10/100;
		float high = Gdx.graphics.getHeight() * 10/100;
		for(String b : skyBlocks){
			Sprite block = new Sprite(groundAtlas.findRegion(b));
			block.setSize(blockSize, blockSize);
			block.setX(pos);
			block.setY(high);
			pos += block.getWidth();
			block.draw(batch);
		}
		
		pos = Gdx.graphics.getWidth() * 30/100;
		high = Gdx.graphics.getHeight() * 30/100;
		for(String b : skyBlocks){
			Sprite block = new Sprite(groundAtlas.findRegion(b));
			block.setSize(blockSize, blockSize);
			block.setX(pos);
			block.setY(high);
			pos += block.getWidth();
			block.draw(batch);
		}
		
		for(Enemy e : enemies)
		{
			e.draw(batch, 0);
		}
	}
	
	@Override
	public Enemy createEnemy(World world)
	{
		Random rn = new Random();
		String name = enemyType[rn.nextInt(enemyType.length)];
		AtlasRegion r = enemyAtlas.findRegion(name);
	    Sprite s = new Sprite(r);
		Enemy e = new Enemy(s, rn.nextInt(1), -(float)rn.nextInt(10) * blockSize, (float)rn.nextInt(Gdx.graphics.getHeight()) + blockSize);
		e.initialize(world);
		return e;
	}
	
	@Override
	public void act(float delta)
	{
		enemyDeltaTime += delta;
		if(enemyDeltaTime > 5);
			enemyDeltaTime = 0;
		
		if(enemyDeltaTime != 0)
			return;
		if(enemies.size() >= enemiesLiveCount)
			return;
		
		if(enemiesCount < 1)
			return;
		int count = enemiesLiveCount;
		if(enemiesCount < enemiesLiveCount)
			count = enemiesCount;
		for(int i = 0; i < count; i++)
		{
			enemies.add(createEnemy(world));
			enemiesCount--;
		}
	}
}
