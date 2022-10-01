package com.r3nny.seabattle.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabattle.client.controller.CellsController;
import com.r3nny.seabattle.client.model.*;


public class SeaBattle extends ApplicationAdapter {

	//TODO: Добавить ограничение на два обьекта класса GameField, а то лохануся и получилось два gameField игрока.
	public static final float WORLD_WIDTH = 1024;
	public static final float WORLD_HEIGHT = 576;
	SpriteBatch batch;
	Texture img;
	Stage stage;
	
	@Override
	public void create () {
		stage = new Stage(new FitViewport(WORLD_WIDTH,WORLD_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		Game.playerField = new GameField(89,WORLD_HEIGHT -175);


		stage.addActor(Game.playerField);
		stage.setDebugAll(true);

		System.out.println(CellsController.getCellByCoord(105,305));




	}

	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(new Color(Color.BLACK));
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
