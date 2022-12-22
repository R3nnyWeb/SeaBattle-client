/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.ui.ChangeScreenButton;

/**Экран с выбором режима игры*/
public class ChooseScreen implements Screen {
    private ImageButton singleGame;
    private ImageButton multiGame;
    private Image bgImage;
    private final StarBattle application;
    private final Stage stage;
    private ChangeScreenButton backButton;

    public ChooseScreen(StarBattle application) {
        this.application = application;
        stage = StarBattle.setUpStage();

        setUpBgImage();
        setUpSingleGameButton();
        setUpMultiGameButton();
        setUpBackButton();

    }

    private void setUpBgImage() {
        bgImage = new Image(StarBattle.assetsManager.getMenuBackground());
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);

        stage.addActor(bgImage);
    }

    private void setUpSingleGameButton() {
        singleGame = new ImageButton(StarBattle.assetsManager.getChooseButtonSkin());
        singleGame.setSize(261, 257);
        singleGame.setX(StarBattle.WORLD_WIDTH / 2 - 25 - singleGame.getWidth());
        singleGame.setY(StarBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);
        singleGame.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        StarBattle.soundManager.playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        StarBattle.soundManager.stopMainMusic();
                        StarBattle.soundManager.playNewGameSound();
                        application.setScreen(new ShipsCreatingScreen(application));
                    }
                });
        singleGame.addAction(StarBattle.animationManager.getFadeInAnimation());

        stage.addActor(singleGame);
    }

    private void setUpMultiGameButton() {
        multiGame = new ImageButton(StarBattle.assetsManager.getChooseButtonSkin(), "multiPlayer");

        multiGame.setSize(singleGame.getWidth(), singleGame.getHeight());
        multiGame.setX(StarBattle.WORLD_WIDTH / 2 + 25);
        multiGame.setY(StarBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);

        multiGame.addAction(StarBattle.animationManager.getFadeInAnimation());

        stage.addActor(multiGame);
    }
    private void setUpBackButton() {
        backButton =
                new ChangeScreenButton("Back to menu", smoothFadeOut(), () -> {
                    stage.clear();
                    application.setScreen(new MenuScreen(application));
                });
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        stage.addActor(backButton);
    }

    public Runnable smoothFadeOut() {
        return () -> {
            backButton.removeWithFade();
            bgImage.addAction(StarBattle.animationManager.getFadeOutAnimation());
            singleGame.addAction(StarBattle.animationManager.getFadeOutAnimation());
            multiGame.addAction(StarBattle.animationManager.getFadeOutAnimation());
        };
    }

    @Override
    public void show() {
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
