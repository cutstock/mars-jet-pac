package com.jetpac.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LazerRay extends GameObject {

	private final String[] bullets = {"bullet0", "bullet1", "bullet2", "bullet4", "bullet5", "bullet6", "bullet7"};
	private String bulletType;
	private boolean straight;
	protected static TextureAtlas atlas;
	private static Sound sound;
	
	public LazerRay(int direction, float x, float y) {
		if(sound == null)
			sound = Gdx.audio.newSound(Gdx.files.internal("Laser.wav"));
		if(atlas == null)
			atlas = new TextureAtlas("bullets.pack");
		this.x = x;
		this.y = y;
		//Random rn = new Random();
		bulletType = "bullet0";//bullets[rn.nextInt(bullets.length)];
		straight = direction == 1;
	}
	
	@Override
	void initialize(World world) {
		// TODO Auto-generated method stub
		// Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    
	    AtlasRegion r = atlas.findRegion(bulletType);
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
	    body.setUserData(this);
	    body.setFixedRotation(true);
	    
	    fixture = body.createFixture(fixtureDef);
	    
	    // Shape is the only disposable of the lot, so get rid of it
	    shape.dispose();
	    
	    Vector2 lv = body.getLinearVelocity();
	    lv.x = straight? 5000f : -5000f;
	    body.setLinearVelocity(lv);
	}
	
	@Override
	public void draw (Batch batch, float parentAlpha){
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		//sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        //sprite.setRotation((float)Math.toDegrees(body.getAngle()));
		//setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		sprite.draw(batch);
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		Vector2 lv = body.getLinearVelocity();
		lv.x = lv.x * 2f;
	    body.setLinearVelocity(lv);
	}
	
	public void blastSound()
	{
		if(sound != null)
			sound.play();
	}
}
