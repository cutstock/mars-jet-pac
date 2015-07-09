package com.jetpac.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class MainMenuScreen implements Screen {

	final MarsJetPac game;
	Texture background;

	OrthographicCamera camera;
	
	Music music;

	public MainMenuScreen(final MarsJetPac marsJetPac) {
		game = marsJetPac;
		Gdx.input.setCatchBackKey(false);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		background = new Texture(Gdx.files.internal("mainscreen.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("intro_0.ogg"));
		music.setLooping(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.font.draw(game.batch, "Welcome to Mars JetPac mission!!!", 130,
				300);
		game.font.draw(game.batch, "Tap anywhere to begin!", 160, 250);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.showGame();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		if(!music.isPlaying())
			music.play();
	}

	@Override
	public void hide() {
		if(music.isPlaying())
			music.stop();
	}

	@Override
	public void pause() {
		if(music.isPlaying())
			music.stop();
	}

	@Override
	public void resume() {
		if(!music.isPlaying())
			music.play();
	}

	@Override
	public void dispose() {
		music.stop();
		music.dispose();
		background.dispose();
	}
}
