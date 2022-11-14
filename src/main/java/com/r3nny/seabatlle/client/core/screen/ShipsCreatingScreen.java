/* (C)2022 */
package com.r3nny.seabatlle.client.core.screen;

import static com.r3nny.seabatlle.client.core.Game.playerField;
import static com.r3nny.seabatlle.client.core.Game.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SingleGame;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;

public class ShipsCreatingScreen implements Screen {

    private final Image menuLogo;

    private final SingleGame game;

    TextButton acceptButton;

    private final Image bg;
    public final Stage stage;

    public ShipsCreatingScreen() {
        status = GameStatus.SHIPS_STAGE;
        this.stage = StarBattle.setUpStage();

        TextButton backButton =
                new TextButton("Back to menu", StarBattle.assetsManager.getMenuButtonSkin());
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        acceptButton = new TextButton("Accept", StarBattle.assetsManager.getMenuButtonSkin());
        acceptButton.setSize(200, 50);
        acceptButton.setX(StarBattle.WORLD_WIDTH - acceptButton.getWidth() - 10);
        acceptButton.setY(10);
        acceptButton.setVisible(false);
        acceptButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playerField.setShipsReady(true);
                    }
                });

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
                        StarBattle.soundManager.stopBattleMusic();
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
                                                    StarBattle seabatlle =
                                                            ((StarBattle)
                                                                    Gdx.app
                                                                            .getApplicationListener());
                                                    seabatlle.setScreen(new MenuScreen());
                                                })));
                    }
                });

        menuLogo = new Image(StarBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        bg = new Image(StarBattle.assetsManager.getInGameBackground());
        bg.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);

        game = new SingleGame();

        stage.addActor(bg);
        stage.addActor(menuLogo);
        stage.addActor(playerField);
        stage.addActor(acceptButton);
        stage.addActor(Game.enemy);
        stage.addActor(backButton);
        stage.setDebugAll(StarBattle.DEBUG);
    }

    @Override
    public void show() {
        StarBattle.soundManager.playBattleMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.A) && !ShipsCreator.isShipLanding) {
            acceptButton.setVisible(true);
            Gdx.app.log("SingleGameScreen", "Autocreating Player ships");
            playerField.initAutoShips();
        }

        if (game.isShipsReady()) {
            playerField.clearAllNotAllowed();
            StarBattle seabatlle = ((StarBattle) Gdx.app.getApplicationListener());
            stage.clear();
            seabatlle.setScreen(new SingleGameScreen(game));
        }
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
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
