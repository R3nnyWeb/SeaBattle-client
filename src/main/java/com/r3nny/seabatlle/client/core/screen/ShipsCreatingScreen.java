package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.SingleGame;

import static com.r3nny.seabatlle.client.core.Game.playerField;
import static com.r3nny.seabatlle.client.core.Game.status;


public class ShipsCreatingScreen implements Screen {



    private final SingleGame game;
    private final SpriteBatch batch;
    private final Texture bg;
    public final Stage stage;

    public ShipsCreatingScreen() {
        //TODO: GSM????
        status = GameStatus.SHIPS_STAGE;
        this.stage = SeaBattle.setUpStage();




        batch = new SpriteBatch();
        bg = SeaBattle.assetsManager.getInGameBackground();
        game = new SingleGame();
        stage.addActor(playerField);
        stage.addActor(Game.enemy);
        stage.setDebugAll(SeaBattle.DEBUG);
    }

    @Override
    public void show() {
        SeaBattle.soundManager.playBattleMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
        batch.begin();
        batch.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        batch.draw(bg, 0, 0, SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT);
        batch.end();
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //TODO: Тут надо кнопку добавить для потверждения
            Gdx.app.log( "SingleGameScreen", "Autocreating Player ships");

            playerField.initAutoShips();
        }

        if(game.isShipsReady()){
            playerField.clearAllNotAllowed();
            SeaBattle seabatlle = ((SeaBattle) Gdx.app.getApplicationListener());
            stage.clear();
            seabatlle.setScreen(new SingleGameScreen(game));
        }


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        stage.dispose();
    }
}
