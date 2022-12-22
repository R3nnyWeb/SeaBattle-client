/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.actors.ShipsCreatingArea;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;
import com.r3nny.seabatlle.client.core.game.SingleGame;

import static com.r3nny.seabatlle.client.core.game.Game.player;

/**
 * Экран с созданием кораблей игрока
 */
public class ShipsCreatingScreen extends InGameScreen {

    private final SingleGame game;

    private TextButton acceptButton;

    private ShipsCreatingArea shipsCreatingArea;

    public ShipsCreatingScreen() {
        super(new Image(StarBattle.assetsManager.getInGameBackground()));
        Game.status = GameStatus.SHIPS_STAGE;
        setUpAcceptButton();
        setUpShipsCreatingArea();
        setUpLabels();
        game = new SingleGame();
        stage.addActor(Game.player);
        stage.setDebugAll(StarBattle.DEBUG);
    }


    private void setUpAcceptButton() {
        acceptButton = new TextButton("Accept", assetManager().getMenuButtonSkin());
        acceptButton.setSize(200, 50);
        acceptButton.setX(StarBattle.WORLD_WIDTH - acceptButton.getWidth() - 10);
        acceptButton.setY(10);
        acceptButton.setVisible(false);
        acceptButton.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        player.setShipsReady(true);
                    }
                });

        stage.addActor(acceptButton);
    }


    private void setUpShipsCreatingArea() {
        shipsCreatingArea = new ShipsCreatingArea(Game.ENEMY_FIELD_X, Game.FIELD_Y);
        stage.addActor(shipsCreatingArea);
    }

    private void setUpLabels() {

        Label.LabelStyle skin = new Label.LabelStyle();
        skin.font = StarBattle.assetsManager.getFont(40);

        Label playerFieldLabel = new Label("Your Field", skin);
        playerFieldLabel.setFontScale(0.5F);
        playerFieldLabel.setPosition(
                Game.PLAYER_FIELD_X, Game.FIELD_Y + playerFieldLabel.getHeight() - 20);
        stage.addActor(playerFieldLabel);

    }


    @Override
    public void show() {
        soundManager().playBattleMusic();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (!ShipsCreator.isAnyShipLanding) {
                acceptButton.setVisible(true);
                Game.player.createShipsAutomaticaly();
            }
        }
        if (game.isShipsReady()) {
            Game.player.clearAllMissed();
            shipsCreatingArea.remove();
            application.setScreen(new SingleGameScreen(game));
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
        stage.dispose();
    }
}
