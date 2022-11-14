/* (C)2022 */
package com.r3nny.seabatlle.client.core.screen;

import static com.r3nny.seabatlle.client.core.Game.playerField;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.Ship;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;

public class SingleGameScreen implements Screen {

    // TODO: Ну гавно же
    private float i;
    // TODO: Ну гавно же
    private float j;
    private final SingleGame game;

    private final Stage stage;

    private Assets assetsManager;

    private Image bgImage;

    private SoundManager soundManager;

    public SingleGameScreen(SingleGame game) {
        this.game = game;

        // TODO: Рандомом

        Game.status = GameStatus.PLAYER_TURN;

        stage = SeaBattle.setUpStage();
        assetsManager = SeaBattle.assetsManager;
        soundManager = SeaBattle.soundManager;

        TextButton backButton =
                new TextButton("Back to menu", SeaBattle.assetsManager.getMenuButtonSkin());
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(SeaBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        Image menuLogo = new Image(SeaBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(SeaBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(SeaBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        // TODO: Два одинаковых - кринж
        backButton.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        SeaBattle.soundManager.playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        SeaBattle.soundManager.playClickSound();
                        playerField.addAction(Actions.fadeOut(0.5F));
                        Game.enemy.addAction(Actions.fadeOut(0.5F));
                        // TODO: Использовать фунцкионгальный интерфейс для анимации при любом
                        // перееходе
                        backButton.addAction(
                                Actions.sequence(
                                        Actions.fadeOut(0.5F),
                                        Actions.run(
                                                () -> {
                                                    stage.clear();
                                                    SeaBattle seabatlle =
                                                            ((SeaBattle)
                                                                    Gdx.app
                                                                            .getApplicationListener());
                                                    SeaBattle.soundManager.stopBattleMusic();
                                                    seabatlle.setScreen(new MenuScreen());
                                                })));
                    }
                });

        // TODO: rework this
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
    public void show() {}

    private boolean isEnemyDead() {
        var enemy = Game.enemy.getShips();
        for (Ship ship : enemy) {
            if (!ship.isKilled()) {
                return false;
            }
        }
        return true;
    }

    private boolean isPlayerDead() {
        var playerShips = playerField.getShips();
        for (Ship ship : playerShips) {
            if (!ship.isKilled()) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return isEnemyDead() || isPlayerDead();
    }

    @Override
    public void render(float v) {
        j += v;

        ScreenUtils.clear(new Color(Color.BLACK));
        game.update();
        // TODO: Проверять только после измений;
        if (j > 1) {
            if (isGameOver()) {
                SeaBattle.soundManager.stopBattleMusic();
                SeaBattle seabatlle = ((SeaBattle) Gdx.app.getApplicationListener());
                seabatlle.setScreen(new MenuScreen());
            }
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
