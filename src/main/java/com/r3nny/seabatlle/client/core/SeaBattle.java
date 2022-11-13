package com.r3nny.seabatlle.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.r3nny.seabatlle.client.core.screen.SplashScreen;
import com.r3nny.seabatlle.client.core.utils.AnimationSpritesManager;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;


public class SeaBattle extends com.badlogic.gdx.Game {

    public static Assets assetsManager;
    public static SoundManager soundManager;
    public static AnimationSpritesManager animationManager;

    public static final float WORLD_WIDTH = 1024;
    public static final float WORLD_HEIGHT = 576;






    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}STARW";
    public static boolean DEBUG = false;


    public static Stage setUpStage(){
        Stage stage = new Stage(new FitViewport(SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        return stage;
    }



    @Override
    public void create() {
        this.setScreen(new SplashScreen());
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
