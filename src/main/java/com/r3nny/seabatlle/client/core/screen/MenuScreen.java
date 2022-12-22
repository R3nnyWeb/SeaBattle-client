/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;
import com.r3nny.seabatlle.client.core.ui.ChangeScreenButton;
import com.r3nny.seabatlle.client.core.utils.Assets;

/**
 * Экран Меню
 */
public class MenuScreen extends Screen {
    private Image menuLogo;
    private ChangeScreenButton start = null;
    private ChangeScreenButton end;


    public MenuScreen() {
        super();
        Game.status = GameStatus.MENU;
        stage.addAction(Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F)));
        setUpBgImage();
        setUpMenuLogo();
        setUpLabel();
        setUpButtons();
    }

    private void setUpBgImage() {
        Image bgImage = new Image(assetManager().getMenuBackground());
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);

        stage.addActor(bgImage);
    }

    private void setUpMenuLogo() {
        menuLogo = new Image(assetManager().getMenuLogo());
        menuLogo.setSize(620, 55);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        stage.addActor(menuLogo);
    }

    private void setUpLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = new Color(Color.WHITE);
        labelStyle.font = assetManager().getFont(16);
        Label label = new Label("by Nikita Vashkulatov, design by Danil Ionov", labelStyle);
        label.setPosition(10, 0);
        stage.addActor(label);
    }

    private void setUpButtons() {
        end = new ChangeScreenButton("Exit", smoothFadeOut(), () -> Gdx.app.exit());
        start = new ChangeScreenButton("New game", smoothFadeOut(), () -> MenuScreen.this.application.setScreen(new ChooseScreen()));

        start.setX(StarBattle.WORLD_WIDTH - 350);
        start.setSize(300, 50);
        end.setX(start.getX());
        start.setY(100);
        end.setY(start.getY() - 60);

        stage.addActor(start);
        stage.addActor(end);
    }


    public Runnable smoothFadeOut() {
        return () -> {
            start.removeWithFade();
            end.removeWithFade();
            menuLogo.addAction(animationManager().getFadeOutAnimation());
        };
    }

    @Override
    public void show() {
        soundManager().playMainMusic();
        Gdx.app.log("Menu Screen", "Showing menu screen");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
