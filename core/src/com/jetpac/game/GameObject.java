package com.jetpac.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
	protected TextureAtlas atlas;
	
	public void dispose(){
		atlas.dispose();
	}
}
