/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SingleGame;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import com.r3nny.seabatlle.client.core.model.ShipsCreatingArea;
import com.r3nny.seabatlle.client.core.ui.ChangeScreenButton;

import static com.r3nny.seabatlle.client.core.Game.player;
import static com.r3nny.seabatlle.client.core.Game.status;

public class ShipsCreatingScreen implements Screen {

    private final SingleGame game;

    private TextButton acceptButton;
    private final StarBattle application;
    public final Stage stage;

    public ShipsCreatingScreen(StarBattle application) {
        this.application = application;
        status = GameStatus.SHIPS_STAGE;
        this.stage = StarBattle.setUpStage();

        setUpBg();
        setUpMenuLogo();
        setUpBackButton();
        setUpAcceptButton();
        setUpShipsCreatingArea();
        setUpLabels();

        game = new SingleGame();
        stage.addActor(Game.player);
        stage.setDebugAll(StarBattle.DEBUG);
    }



    private void setUpAcceptButton() {
        acceptButton = new TextButton("Accept", StarBattle.assetsManager.getMenuButtonSkin());
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


    private void setUpBg() {
      Image bg = new Image(StarBattle.assetsManager.getInGameBackground());
        bg.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);
        stage.addActor(bg);
    }

    private void setUpMenuLogo() {
        Image menuLogo = new Image(StarBattle.assetsManager.getMenuLogo());
        menuLogo.setSize(310, 27);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        stage.addActor(menuLogo);
    }

    private void setUpBackButton() {
       ChangeScreenButton backButton =
                new ChangeScreenButton("Back to menu",() -> {}, () -> {
                    stage.clear();
                    application.setScreen(new MenuScreen(application));
                });
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        stage.addActor(backButton);
    }

    private void setUpShipsCreatingArea() {
        ShipsCreatingArea shipsCreatingArea = new ShipsCreatingArea(Game.ENEMY_FIELD_X, Game.FIELD_Y);
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
        StarBattle.soundManager.playBattleMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (!ShipsCreator.isShipLanding) {
                acceptButton.setVisible(true);
                Game.player.createShipsAutomaticaly();
            }
        }

        if (game.isShipsReady()) {
            Game.player.clearAllMissed();
            stage.clear();
            application.setScreen(new SingleGameScreen(game, application));
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
