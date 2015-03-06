package com.jetpac.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
	
	public void dispose(){
		atlas.dispose();
	}
	
	abstract void initialize(World world);
}
