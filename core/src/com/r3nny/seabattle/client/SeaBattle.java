package com.r3nny.seabattle.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabattle.client.controller.CellsController;
import com.r3nny.seabattle.client.model.*;

import java.security.Key;

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
		playerField = new GameField(89,WORLD_HEIGHT -100);
		batch = new SpriteBatch();




		stage.addActor(playerField);
		stage.setDebugAll(true);
		//stage.setDebugUnderMouse(true);


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
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			playerField.initAutoShips();
		}


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
	}
}
