/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;
import com.r3nny.seabatlle.client.core.game.SingleGame;

import java.util.Random;

/**
 * Экран при одиночной игре
 */
public class SingleGameScreen extends InGameScreen {
    private final SingleGame game;

    private Label turnLabel;

    public SingleGameScreen(SingleGame game) {
        super(new Image(StarBattle.assetsManager.getInGameBackground()));
        this.game = game;

        Game.player.createShipsManager();
        Game.enemy.createShipsManager();
        Game.status = randomGameStatus();

        disableClicksOnPlayerField();
        setUpLabels();
        stage.addActor(Game.player);

        stage.addActor(Game.enemy);
        stage.setDebugAll(StarBattle.DEBUG);
    }

    private void setUpLabels() {
        Label.LabelStyle skin = new Label.LabelStyle();
        skin.font = assetManager().getFont(40);

        Label playerFieldLabel = new Label("Your Field", skin);
        playerFieldLabel.setFontScale(0.5F);
        playerFieldLabel.setPosition(
                Game.PLAYER_FIELD_X, Game.FIELD_Y + playerFieldLabel.getHeight() - 20);
        Label enemyFieldLabel = new Label("Enemy Field", skin);
        enemyFieldLabel.setFontScale(0.5F);
        enemyFieldLabel.setPosition(
                Game.ENEMY_FIELD_X, Game.FIELD_Y + enemyFieldLabel.getHeight() - 20);

        turnLabel = new Label("", skin);
        turnLabel.setFontScale(0.5F);
        turnLabel.setWidth(Game.ENEMY_FIELD_X - Game.PLAYER_FIELD_X + Cell.SIZE * 10);
        turnLabel.setPosition(
                StarBattle.WORLD_WIDTH / 2 + 3 - turnLabel.getWidth() / 2, Game.FIELD_Y);
        turnLabel.setAlignment(Align.center);
        stage.addActor(playerFieldLabel);
        stage.addActor(enemyFieldLabel);
        stage.addActor(turnLabel);

    }

    private void disableClicksOnPlayerField() {
        Cell[][] cells = Game.player.getField();
        for (Cell[] cell : cells) {
            for (int j = 0; j < cells.length; j++) {
                var listeners = cell[j].getListeners();
                for (EventListener listener : listeners) {
                    cell[j].removeListener(listener);
                }
            }
        }
    }


    private GameStatus randomGameStatus() {
        Random rd = new Random();
        return rd.nextBoolean() ? GameStatus.PLAYER_TURN : GameStatus.ENEMY_TURN;
    }

    @Override
    public void show() {
    }


    private boolean isEnemyDead() {
        return Game.enemy.isAllShipsKilled();
    }

    private boolean isPlayerDead() {
        return Game.player.isAllShipsKilled();
    }

    private boolean isGameOver() {
        return isEnemyDead() || isPlayerDead();
    }

    @Override
    public void render(float v) {
        updateTurnLabel();
        game.update();
        if (isGameOver()) {
            soundManager().stopBattleMusic();
            Game.status = GameStatus.END;
            stage.addAction(
                    Actions.sequence(
                            animationManager().getFadeOutAnimation(),
                            Actions.run(() -> {
                                stage.clear();
                                application.setScreen(new EndScreen(isEnemyDead()));

                            })));
        }
        stage.act();
        stage.draw();
    }

    private void updateTurnLabel() {
        turnLabel.setText(
                (Game.status == GameStatus.PLAYER_TURN) ? "Shoot, Admiral" : "Enemy shooting");
        ScreenUtils.clear(new Color(Color.BLACK));
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
