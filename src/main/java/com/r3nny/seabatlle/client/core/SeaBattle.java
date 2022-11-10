package com.r3nny.seabatlle.client.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.r3nny.seabatlle.client.core.Game.playerField;



@Slf4j
public class SeaBattle extends ApplicationAdapter {

    //TODO: Добавить ограничение на два обьекта класса GameField, а то лохануся и получилось два gameField игрока.
    public static final float WORLD_WIDTH = 1024;
    public static final float WORLD_HEIGHT = 576;

    SingleGame game;
    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}STARW";
    public static boolean debug = true;
    SpriteBatch batch;
    Texture bg;
    Stage stage;



    @Override
    public void create() {

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle label1Style = new Label.LabelStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
       FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 26;
        parameter.characters = RUSSIAN_CHARACTERS;
        label1Style.font = generator.generateFont(parameter); ;
        label1Style.fontColor = Color.WHITE;
        Label label1 = new Label(" круто   STAR WARS",label1Style);
        label1.setPosition(0,0);
        stage.addActor(label1);
        batch = new SpriteBatch();
        bg = new Texture("bg.jpg");
        game = new SingleGame();
        stage.addActor(playerField);
        stage.addActor(Game.enemy);
        stage.setDebugAll(debug);
        //stage.setDebugUnderMouse(true);


    }

    public void resize(int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(new Color(Color.BLACK));
        batch.begin();
        batch.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        batch.draw(bg, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.end();
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            log.info("Autocreating Player ships");
            playerField.initAutoShips();
        }
        if(game.isShipsReady()){
            log.info("Both players are ready");
        }





    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        stage.dispose();

    }
}
