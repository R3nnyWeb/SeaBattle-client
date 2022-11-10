package com.r3nny.seabatlle.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabatlle.client.core.screen.MenuScreen;
import com.r3nny.seabatlle.client.core.screen.SingleGameScreen;


public class SeaBattle extends com.badlogic.gdx.Game {

    public static final float WORLD_WIDTH = 1024;
    public static final float WORLD_HEIGHT = 576;

   public Stage stage;

    SingleGame game;
    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}STARW";
    public static boolean DEBUG = true;






    @Override
    public void create() {
        stage = new Stage(new FitViewport(SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        this.setScreen(new MenuScreen(this));



    }

    public void resize(int width, int height) {
     super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {


    }
}
