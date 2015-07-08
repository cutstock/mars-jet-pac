package com.jetpac.game;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import sun.net.www.content.audio.x_aiff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Spaceman extends GameObject {
	
	private final String[] runRightMoves = {"run_right0", "run_right1", "run_right2", "run_right3", "run_right4", "run_right5", "run_right6", "run_right7"};
	private final String[] runLeftMoves = {"run_left0", "run_left1", "run_left2", "run_left3", "run_left4", "run_left5", "run_left6", "run_left7"};
	private final String[] jumpRightMoves = {"jump_right"};
	private final String[] jumpLeftMoves = {"jump_left"};
	private final String[] standRightMoves = {"stand_right"};
	private final String[] standLeftMoves = {"stand_left"};
	private final String[] sitRightMoves = {"sit_right"};
	private final String[] sitLeftMoves = {"sit_left"};
	private final String[] flyRightMoves = {"fly_right"};
	private final String[] flyLeftMoves = {"fly_Left"};
	
	private MoveState runRightState;
	private MoveState runLeftState;
	private MoveState jumpRightState;
	private MoveState jumpLeftState;
	private MoveState standRightState;
	private MoveState standLeftState;
	private MoveState sitRightState;
	private MoveState sitLeftState;
	private MoveState flyRightState;
	private MoveState flyLeftState;
	
	private int lastMoveDirection = 1; // default move right

	private String moveState;
	
	public Spaceman(){
		atlas = new TextureAtlas("soldier.pack");
		setTouchable(Touchable.enabled);
		
		runRightState = new MoveState(runRightMoves);
		runLeftState = new MoveState(runLeftMoves);
		jumpRightState = new MoveState(jumpRightMoves);
		jumpLeftState = new MoveState(jumpLeftMoves);
		standRightState = new MoveState(standRightMoves);
		standLeftState = new MoveState(standLeftMoves);
		sitRightState = new MoveState(sitRightMoves);
		sitLeftState = new MoveState(sitLeftMoves);
		flyRightState = new MoveState(flyRightMoves);
		flyLeftState = new MoveState(flyLeftMoves);
		
		moveState = standRightState.getState();
	}
	
	public void initialize(World world){
		// Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    
	    Sprite sprite = new Sprite(atlas.findRegion("run_right4"));
	    // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine is 1 pixel
	    // Set our body to the same position as our sprite
	    bodyDef.position.set(100, 100);
	
	    // Now define the dimensions of the physics shape
	    PolygonShape shape = new PolygonShape();
	    // We are a box, so this makes sense, no?
	    // Basically set the physics polygon to a box with the same dimensions as our sprite
	    shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
	    
	    this.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
	
	    // FixtureDef is a confusing expression for physical properties
	    // Basically this is where you, in addition to defining the shape of the body
	    // you also define it's properties like density, restitution and others we will see shortly
	    // If you are wondering, density and area are used to calculate over all mass
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;
	    
	    // Create a body in the world using our definition
	    body = world.createBody(bodyDef);
	    
	    fixture = body.createFixture(fixtureDef);
	
	    // Shape is the only disposable of the lot, so get rid of it
	    shape.dispose();
	}

	@Override
	public void draw (Batch batch, float parentAlpha){
		Sprite sprite = new Sprite(atlas.findRegion(moveState));
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
        //sprite.setRotation((float)Math.toDegrees(body.getAngle()));
		//setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		sprite.draw(batch);
	}
	
	 
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		
		x = body.getPosition().x;
		y = body.getPosition().y;
		
		Vector2 lv = body.getLinearVelocity();
		
		String tmpMoveState = null;
			
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			lastMoveDirection = 1;
			tmpMoveState = runRightState.getNextState(delta);
			lv.x = 50f;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			lastMoveDirection = -1;
			tmpMoveState = runLeftState.getNextState(delta);
			lv.x = -50f;
		}
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			tmpMoveState = lastMoveDirection == 1? jumpRightState.getNextState(delta) : jumpLeftState.getNextState(delta);
			lv.y = 50f;
			//body.applyForceToCenter(0f,1000f,true);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			tmpMoveState = lastMoveDirection == 1? sitRightState.getNextState(delta) : sitLeftState.getNextState(delta);
			//lv.y = 50f;
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && !Gdx.input.isKeyPressed(Keys.UP))
		{
			//TODO: shoooooot!!!
			this.fire(new Event());
		}
		
		if(tmpMoveState != null)
		{
			moveState = tmpMoveState;
		}
		else
		{
			moveState = lastMoveDirection == 1? standRightState.getState() : standLeftState.getState();
			// reset all states
			runRightState.reset();
			runLeftState.reset();
			int vx = (int)lv.x;
			if(vx != 0)
			{
				lv.x = lastMoveDirection == 1? lv.x - 1.0f : lv.x + 1.0f;
			}
		}
		body.setLinearVelocity(lv);
	}
	
	public int GetDirection()
	{
		return lastMoveDirection;
	}
}
