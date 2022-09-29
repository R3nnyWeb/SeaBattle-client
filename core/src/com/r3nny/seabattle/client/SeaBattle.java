package com.r3nny.seabattle.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabattle.client.controller.GameFieldController;
import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.view.GameFieldView;



public class SeaBattle extends ApplicationAdapter {

	public static final float WORLD_WIDTH = 1024;
	public static final float WORLD_HEIGHT = 576;
	SpriteBatch batch;
	Texture img;



	Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new FitViewport(WORLD_WIDTH,WORLD_HEIGHT));
		Gdx.input.setInputProcessor(stage);


		Game.playerField = new GameField();
		Game.playerView = new GameFieldView(100,500, Game.playerField);
		GameFieldController playerFieldController = new GameFieldController( Game.playerField,Game.playerView);
		stage.addActor(Game.playerView);

	}

	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1f, 1f, 1f, 1);
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
