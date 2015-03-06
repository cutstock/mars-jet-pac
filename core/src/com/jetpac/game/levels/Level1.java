package com.jetpac.game.levels;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jetpac.game.GameLevel;

public class Level1 extends GameLevel {
	
	private int blockSize = 32;
	private int blocksCount;
	private List<String> blocks;
	
	@Override
	public void Initialize()
	{
		atlas = new TextureAtlas("ground_brown.pack");
		background = new Texture(Gdx.files.internal("background0.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("game-game_0.ogg"));
		music.setLooping(true);
		blocks = new ArrayList<String>();
		blocksCount = Gdx.graphics.getWidth()/blockSize;
		
		blocks.add(ground[2]);
		for(int i = 0; i < blocksCount - 2; i++){
			blocks.add(ground[3]);
		}
		blocks.add(ground[4]);
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		// TODO Auto-generated method stub
		super.draw(batch, delta);
		float pos = 0;
		for(String b : blocks){
			Sprite block = new Sprite(atlas.findRegion(b));
			block.setSize(blockSize, blockSize);
			block.setX(pos);
			pos += block.getWidth();
			block.draw(batch);
		}
	}
}
