package com.r3nny.seabattle.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabattle.client.model.*;
import com.r3nny.seabattle.client.view.ShipView;

import static com.r3nny.seabattle.client.Game.playerField;


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
		playerField = new GameField(100,400);
		ShipView sh = new ShipView(100,100,new Ship(null, ShipType.FOUR_DECK));
		stage.addActor(sh);
		stage.addActor(playerField);
		System.out.println("f");



	}

	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(new Color(Color.LIGHT_GRAY));
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
