/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;
import com.r3nny.seabatlle.client.core.game.SingleGame;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.ui.ChangeScreenButton;

import java.util.Random;

/**Экран при одиночной игре*/
public class SingleGameScreen implements Screen {

    private final SingleGame game;

    private final Stage stage;
    private final StarBattle application;

    private Label turnLabel;

    public SingleGameScreen(SingleGame game, StarBattle application) {
        this.game = game;
        this.application = application;
        Game.player.createShipsManager();
        Game.enemy.createShipsManager();
        Game.status = randomGameStatus();
        stage = StarBattle.setUpStage();
        setUpBgImage();
        setUpLogo();
        setUpBackButton();
        disableClicksOnPlayerField();
        setUpLabels();
        stage.addActor(Game.player);

        stage.addActor(Game.enemy);
        stage.setDebugAll(StarBattle.DEBUG);
    }

    private void setUpLabels() {
        Label.LabelStyle skin = new Label.LabelStyle();
        skin.font = StarBattle.assetsManager.getFont(40);

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

    private void setUpBackButton() {
        ChangeScreenButton backButton =
                new ChangeScreenButton("Back to menu", () -> {
                }, () -> {
                    stage.clear();
                    application.setScreen(new MenuScreen(application));
                });
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());
        stage.addActor(backButton);
    }

    private void setUpLogo() {
        Image menuLogo = new Image(StarBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);
        stage.addActor(menuLogo);
    }

    private void setUpBgImage() {
        Image bgImage = new Image(StarBattle.assetsManager.getInGameBackground());
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);
        stage.addActor(bgImage);
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
            StarBattle.soundManager.stopBattleMusic();
            application.setScreen(new MenuScreen(application));
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
