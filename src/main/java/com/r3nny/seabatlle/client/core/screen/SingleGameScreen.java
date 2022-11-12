package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.SingleGame;
import com.r3nny.seabatlle.client.core.controller.GameController;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;

import java.util.Random;

import static com.r3nny.seabatlle.client.core.Game.playerField;

public class SingleGameScreen implements Screen {

    private float i;
    private final SingleGame game;

    private final Stage stage;

    private Assets assetsManager;

    private Image bgImage;

    private SoundManager soundManager;

    public SingleGameScreen(SingleGame game) {
        this.game = game;

        //TODO: Рандомом

        Game.status = GameStatus.PLAYER_TURN;

        stage = SeaBattle.setUpStage();
        assetsManager = SeaBattle.assetsManager;
        soundManager = SeaBattle.soundManager;

        TextButton backButton = new TextButton("Back to menu", SeaBattle.assetsManager.getMenuButtonSkin());
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(SeaBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        Image menuLogo = new Image(SeaBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(SeaBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(SeaBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerField.addAction(Actions.fadeOut(0.5F));
                Game.enemy.addAction(Actions.fadeOut(0.5F));
                //TODO: Использовать фунцкионгальный интерфейс для анимации при любом перееходе
                backButton.addAction(Actions.sequence(
                        Actions.fadeOut(0.5F),
                        Actions.run(() -> {
                            stage.clear();
                            SeaBattle seabatlle = ((SeaBattle) Gdx.app.getApplicationListener());
                            seabatlle.setScreen(new MenuScreen());
                        })));
            }
        });

        //TODO: rework this
        Cell[][] cells = playerField.getField();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {

                var listeners = cells[i][j].getListeners();
                for (EventListener listener : listeners) {
                    cells[i][j].removeListener(listener);
                }
            }
        }


        bgImage = new Image(SeaBattle.assetsManager.getInGameBackground());
        bgImage.setSize(SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT);
        stage.addActor(bgImage);
        stage.addActor(menuLogo);
        stage.addActor(playerField);
        stage.addActor(Game.enemy);
        stage.addActor(backButton);
        stage.setDebugAll(SeaBattle.DEBUG);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {


        //TODO: Вынести в update у single
        ScreenUtils.clear(new Color(Color.BLACK));
        if (Game.status == GameStatus.ENEMY_TURN) {
            i += v;
        }
        if (Game.status == GameStatus.ENEMY_TURN && i > 1) {
            Random rd = new Random();
            //TODO: logging
            GameController.shoot(rd.nextInt(10),rd.nextInt(10));
            i = 0;
        }
        stage.act();
        stage.draw();


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

    }

    @Override
    public void dispose() {

    }
}
