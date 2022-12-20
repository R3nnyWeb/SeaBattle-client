/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.screen;

import static com.r3nny.seabatlle.client.core.Game.enemy;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SingleGame;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.Ship;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;
import java.util.Random;

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

    private Label turnLabel;

    public SingleGameScreen(SingleGame game) {
        this.game = game;

        playerField.createShipsManager();
        enemy.createShipsManager();

        Random rd = new Random();
        Game.status = rd.nextBoolean() ? GameStatus.PLAYER_TURN : GameStatus.ENEMY_TURN;

        stage = StarBattle.setUpStage();
        assetsManager = StarBattle.assetsManager;
        soundManager = StarBattle.soundManager;

        TextButton backButton =
                new TextButton("Back to menu", StarBattle.assetsManager.getMenuButtonSkin());
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        Image menuLogo = new Image(StarBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        // TODO: Два одинаковых - кринж
        backButton.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        StarBattle.soundManager.playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        StarBattle.soundManager.playClickSound();
                        playerField.addAction(Actions.fadeOut(0.5F));
                        enemy.addAction(Actions.fadeOut(0.5F));
                        backButton.addAction(
                                Actions.sequence(
                                        Actions.fadeOut(0.5F),
                                        Actions.run(
                                                () -> {
                                                    stage.clear();
                                                    StarBattle seabatlle =
                                                            ((StarBattle)
                                                                    Gdx.app
                                                                            .getApplicationListener());
                                                    StarBattle.soundManager.stopBattleMusic();
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

        bgImage = new Image(StarBattle.assetsManager.getInGameBackground());
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);
        stage.addActor(bgImage);
        stage.addActor(menuLogo);
        stage.addActor(playerField);
        stage.addActor(playerFieldLabel);
        stage.addActor(enemyFieldLabel);
        stage.addActor(turnLabel);
        stage.addActor(enemy);
        stage.addActor(backButton);
        stage.setDebugAll(StarBattle.DEBUG);
    }

    @Override
    public void show() {}

    // TODO : Проверять через GAME_SHIP_MANAGER
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
        turnLabel.setText(
                (Game.status == GameStatus.PLAYER_TURN) ? "Shoot, Admiral" : "Enemy shooting");
        ScreenUtils.clear(new Color(Color.BLACK));
        game.update();
        // TODO: Сделать ShipsManager для отслеживания оставшихся кораблей
        if (j > 1) {
            if (isGameOver()) {
                StarBattle.soundManager.stopBattleMusic();
                StarBattle seabatlle = ((StarBattle) Gdx.app.getApplicationListener());
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
