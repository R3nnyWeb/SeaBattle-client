package com.r3nny.seabattle.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SeaBattle extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new FitViewport(1024,576));
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Image imageActor = new Image(img);
		stage.addActor(new ActorTest(img, 100,100));
	}

	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, .5f, 1);
		stage.act();
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
	}
}
