package com.jetpac.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LazerRay extends GameObject {

	private final String[] bullets = {"bullet0", "bullet1", "bullet2", "bullet4", "bullet5", "bullet6", "bullet7"};
	private String bullet;
	private boolean straight;
	private Sprite sprite;
	private static Sound sound;
	
	public LazerRay(int direction, float x, float y) {
		if(sound == null)
		{
			sound = Gdx.audio.newSound(Gdx.files.internal("Laser.wav"));
		}
		atlas = new TextureAtlas("bullets.pack");
		this.x = x;
		this.y = y;
		Random rn = new Random();
		bullet = bullets[rn.nextInt(bullets.length)];
		//bullet = bullets[5];
		straight = direction == 1;
	}
	
	@Override
	void initialize(World world) {
		// TODO Auto-generated method stub
		// Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    
	    AtlasRegion r = atlas.findRegion(bullet);
	    if(!straight)
			r.flip(true, false);
	    sprite = new Sprite(r);
	    // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine is 1 pixel
	    // Set our body to the same position as our sprite
	    bodyDef.position.set(x, y);
	
	    // Now define the dimensions of the physics shape
	    PolygonShape shape = new PolygonShape();
	    // We are a box, so this makes sense, no?
	    // Basically set the physics polygon to a box with the same dimensions as our sprite
	    shape.setAsBox(sprite.getWidth()/4, sprite.getHeight()/6);
	
	    // FixtureDef is a confusing expression for physical properties
	    // Basically this is where you, in addition to defining the shape of the body
	    // you also define it's properties like density, restitution and others we will see shortly
	    // If you are wondering, density and area are used to calculate over all mass
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 0.1f;
	    
	    // Create a body in the world using our definition
	    body = world.createBody(bodyDef);
	    
	    fixture = body.createFixture(fixtureDef);

	    // Shape is the only disposable of the lot, so get rid of it
	    shape.dispose();
	}
	
	@Override
	public void draw (Batch batch, float parentAlpha){
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
        //sprite.setRotation((float)Math.toDegrees(body.getAngle()));
		//setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		Vector2 lv = body.getLinearVelocity();
	    lv.x = straight? 10000f : -10000f;
	    body.setLinearVelocity(lv);
	}
	
	public void blastSound()
	{
		if(sound != null)
			sound.play();
	}
}
