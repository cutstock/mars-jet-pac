package com.jetpac.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Spaceman extends GameObject {
	
	private String[] rightMoves;
	private String[] leftMoves;
	private String[] jumpRightMoves;
	private String[] jumpLeftMoves;
	
	public Spaceman(){
		atlas = new TextureAtlas("soldier.pack");
		setTouchable(Touchable.enabled);
		setPosition(32, 32);
		
		rightMoves = new String[]{""};
	}
	
	@Override
	public void draw (Batch batch, float parentAlpha){
		Sprite sprite = new Sprite(atlas.findRegion("run_right4"));
		sprite.setPosition(this.getX(), this.getY());
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		sprite.draw(batch);
	}
}
