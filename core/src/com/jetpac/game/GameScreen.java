package com.jetpac.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final MarsJetPac game;
	OrthographicCamera camera;
	GameLevel level;
	Spaceman spaceman;
	World world;

	private float deltaTime;
	
	Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    
    private ArrayList<LazerRay> bullets;
	private EventListener listener;

	public GameScreen(final MarsJetPac marsJetPac) {
		Gdx.input.setCatchBackKey(true);
		
		// Create a Box2DDebugRenderer, this allows us to see the physics simulation controlling the scene
        //debugRenderer = new Box2DDebugRenderer();
		
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		this.game = marsJetPac;
		
		level = game.getLevel();
		if(level == null)
		{
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
		
		// Create a physics world, the heart of the simulation.  The Vector passed in is gravity
        world = new World(new Vector2(0, -100), true);
        
        level.Initialize(world);
        
        bullets = new ArrayList<LazerRay>();
        
        listener = new EventListener() {
			@Override
			public boolean handle(Event event) {
				// TODO Auto-generated method stub
				if(deltaTime < 0.2f)
					return false;
				deltaTime = 0;
				LazerRay bullet = new LazerRay(spaceman.GetDirection(), spaceman.x + (spaceman.GetDirection() * spaceman.getWidth()), spaceman.y);
				bullet.initialize(world);
				bullet.blastSound();
				bullets.add(bullet);
				return true;
			}
		};
        
		spaceman = new Spaceman();
		spaceman.initialize(world);
		spaceman.addListener(listener);
	}

	@Override
	public void render(float delta) {
		deltaTime += delta;
		if(deltaTime > 10f)
			deltaTime = 0;
		// tell the camera to update its matrices.
		camera.update();
		// Advance the world, by the amount of time that has elapsed since the last frame
        // Generally in a real game, don't do this in the render loop, as you are tying the physics
        // update rate to the frame rate, and vice versa
        //world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		world.step(delta, 6, 2);
        
        spaceman.act(delta);
        
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		
		// Scale down the sprite batches projection matrix to box2D size
        debugMatrix = game.batch.getProjectionMatrix().cpy();//.scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		level.draw(game.batch, delta);
		spaceman.draw(game.batch, 0);

		Iterator<LazerRay> bulls = bullets.iterator();
		while(bulls.hasNext())
		{
			LazerRay b = bulls.next();
			b.act(delta);
        	if(b.getDeltaTime() > 4f)
        	{
        		bulls.remove();
        		b.dispose();
        	}
        	else
        	{
        		Vector2 pos = b.getPosition();
        		if(pos.x + b.getWidth() > 800)
        			b.setPosition(0 - b.getWidth(), pos.y);
        		else if (pos.x - b.getWidth() < 0)
        			b.setPosition(800 + b.getWidth(), pos.y);
        		b.draw(game.batch, 0);
        	}
		}
		
		game.font.draw(game.batch, "Score: " + 1, 0, 480);
		game.batch.end();
		
		// Now render the physics world using our scaled down matrix
        // Note, this is strictly optional and is, as the name suggests, just for debugging purposes
        //debugRenderer.render(world, debugMatrix);

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
		}

		// if (Gdx.input.isKeyPressed(Keys.LEFT))
		// bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		// if (Gdx.input.isKeyPressed(Keys.RIGHT))
		// bucket.x += 200 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Keys.BACK)
				|| Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.showMenu();
		}

		// make sure the object stays within the screen bounds

		// check if we need to create a new object

		// move the object, remove any that are beneath the bottom edge of
		// the screen or that hit some object. In the later case we play back
		// a sound effect as well.
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		if(level != null)
			level.playMusic();
	}

	@Override
	public void hide() {
		if(level != null)
			level.stopMusic();
	}

	@Override
	public void pause() {
		if(level != null)
			level.stopMusic();
	}

	@Override
	public void resume() {
		if(level != null)
			level.playMusic();
	}

	@Override
	public void dispose() {
		listener = null;
		Iterator<LazerRay> bulls = bullets.iterator();
		while(bulls.hasNext())
		{
			LazerRay b = bulls.next();
       		bulls.remove();
        	b.dispose();
		}
		spaceman.dispose();
		if(level != null)
		{
			level.stopMusic();
			level.dispose();
		}
		world.dispose();
	}
}
