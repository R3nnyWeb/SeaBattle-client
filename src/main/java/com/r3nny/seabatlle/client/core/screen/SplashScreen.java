/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.utils.AnimationManager;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;

/**
 * Экран загрузки
 * */
public class SplashScreen implements Screen {
    private Image splashImage;
    private Stage stage;
    private StarBattle application;
    private Assets manager;
    private boolean startLoading = false;

    @Override
    public void show() {
        stage = StarBattle.setUpStage();
        StarBattle.assetsManager = new Assets();
        this.manager = StarBattle.assetsManager;
        application = ((StarBattle) Gdx.app.getApplicationListener());
        splashImage = new Image(new Texture(Gdx.files.internal("gameLogo.png")));

        setUpSplashImage();
    }

    private void setUpSplashImage() {
        splashImage.setSize(620, 55);
        splashImage.setX(StarBattle.WORLD_WIDTH / 2 - splashImage.getWidth() / 2);
        splashImage.setY(StarBattle.WORLD_HEIGHT / 2);

        splashImage.addAction(
                Actions.sequence(
                        Actions.alpha(0.0F),
                        Actions.fadeIn(1.25F),
                        Actions.delay(1F),
                        Actions.run(
                                () -> {
                                    manager.loadAllAssets();
                                    startLoading = true;
                                })));

        stage.addActor(splashImage);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act();
        stage.draw();
        if (manager.update() && startLoading) {
            StarBattle.soundManager = new SoundManager();
            StarBattle.animationManager = new AnimationManager();
            stage.addAction(
                    Actions.sequence(
                            Actions.fadeOut(0.5F),
                            Actions.run(
                                    () -> {
                                        stage.clear();
                                        if (StarBattle.DEBUG) {
                                            application.setScreen(new ShipsCreatingScreen());
                                        } else {
                                            application.setScreen(new MenuScreen());
                                        }
                                    })));
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
    public void hide() {}

    @Override
    public void dispose() {}
}
