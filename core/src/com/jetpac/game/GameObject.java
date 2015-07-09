package com.jetpac.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {
	protected TextureAtlas atlas;
	Body body;
	Fixture fixture;
	protected float x;
	protected float y;
	private float deltaTime;
	
	public void dispose(){
		atlas.dispose();
	}
	
	abstract void initialize(World world);
	
	@Override
	public void act(float delta) {
		deltaTime += delta;
		if(deltaTime > 10)
			deltaTime = 0;
	}
	
	public float getDeltaTime()	{
		return deltaTime;
	}
	
	public void setPosition(float x, float y) {
		body.setTransform(x, y, 0);
		super.setX(x);
		super.setY(y);
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
}
