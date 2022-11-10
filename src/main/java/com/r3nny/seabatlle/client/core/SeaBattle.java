package com.r3nny.seabatlle.client.core;

import com.r3nny.seabatlle.client.core.screen.SingleGameScreen;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SeaBattle extends com.badlogic.gdx.Game {

    public static final float WORLD_WIDTH = 1024;
    public static final float WORLD_HEIGHT = 576;

    SingleGame game;
    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}STARW";
    public static boolean DEBUG = true;




    @Override
    public void create() {
        this.setScreen(new SingleGameScreen());



    }

    public void resize(int width, int height) {
        // See below for what true means.
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
