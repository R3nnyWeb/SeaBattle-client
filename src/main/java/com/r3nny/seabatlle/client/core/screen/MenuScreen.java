/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.utils.Assets;

public class MenuScreen implements Screen {

    // TODO: Dынести все??
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture bgTexture;
    private final Image menuLogo;
    private final Image bgImage;

    private final StarBattle game;
    private final Assets manager;

    public MenuScreen() {
        this.manager = StarBattle.assetsManager;
        game = ((StarBattle) Gdx.app.getApplicationListener());
        Game.status = GameStatus.MENU;
        this.stage = StarBattle.setUpStage();
        batch = new SpriteBatch();
        bgTexture = manager.getMenuBackground();
        bgImage = new Image(bgTexture);
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);

        menuLogo = new Image(manager.getMenuLogo());
        menuLogo.setSize(620, 55);
        menuLogo.setX(StarBattle.WORLD_WIDTH / 2 - menuLogo.getWidth() / 2);
        menuLogo.setY(StarBattle.WORLD_HEIGHT - menuLogo.getHeight() - 20);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = new Color(Color.WHITE);
        labelStyle.font = manager.getFont(16);

        Label label = new Label("by Nikita Vashkulatov, design by Danil Ionov", labelStyle);
        label.setPosition(10, 0);

        TextButton start = new TextButton("New game", StarBattle.assetsManager.getMenuButtonSkin());
        TextButton end = new TextButton("Exit", StarBattle.assetsManager.getMenuButtonSkin());

        start.setX(StarBattle.WORLD_WIDTH - 350);
        start.setSize(300, 50);
        end.setSize(300, 50);
        end.setX(start.getX());
        start.setY(100);
        end.setY(start.getY() - 60);
        start.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        StarBattle.soundManager.playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        StarBattle.soundManager.playClickSound();
                        menuLogo.addAction(Actions.fadeOut(0.5F));
                        start.addAction(Actions.fadeOut(0.5F));
                        end.addAction(
                                Actions.sequence(
                                        Actions.fadeOut(0.5F),
                                        Actions.run(
                                                () -> {
                                                    game.setScreen(new ChooseScreen());
                                                })));
                    }
                });
        end.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        StarBattle.soundManager.playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        StarBattle.soundManager.playClickSound();
                        Gdx.app.exit();
                    }
                });

        stage.addAction(Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F)));
        stage.addActor(bgImage);
        stage.addActor(menuLogo);
        stage.addActor(label);
        stage.addActor(start);
        stage.addActor(end);
    }

    @Override
    public void show() {
        StarBattle.soundManager.playMainMusic();

        Gdx.app.log("Menu Screen", "Showing menu screen");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
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
