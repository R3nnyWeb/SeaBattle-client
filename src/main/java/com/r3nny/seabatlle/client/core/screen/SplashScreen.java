package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;

public class SplashScreen implements Screen {

    private Texture splashTexture;
    private Image splashImage;

    private Stage stage;
    private SeaBattle game;
    private Assets manager;
    private boolean startLoading = false;

    @Override
    public void show() {
        stage = SeaBattle.setUpStage();
        SeaBattle.assetsManager = new Assets();
        this.manager = SeaBattle.assetsManager;
        game = ((SeaBattle) Gdx.app.getApplicationListener());
        splashTexture = new Texture(Gdx.files.internal("gameLogo.png"));
        splashImage = new Image(splashTexture);
        splashImage.setSize(620, 55);
        splashImage.setX(SeaBattle.WORLD_WIDTH / 2 - splashImage.getWidth() / 2);
        splashImage.setY(SeaBattle.WORLD_HEIGHT / 2);
        splashImage.addAction(Actions.sequence(
            Actions.alpha(0.0F),
            Actions.fadeIn(1.25F),
            Actions.delay(1F),
            Actions.run(() -> {
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
            SeaBattle.soundManager = new SoundManager();
            stage.addAction(Actions.sequence(
                Actions.fadeOut(0.5F),
                Actions.run(() -> {
                    stage.clear();
                    if(SeaBattle.DEBUG){
                        game.setScreen(new ShipsCreatingScreen());
                    } else {
                        game.setScreen(new MenuScreen());}
                })));
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

    }

    @Override
    public void dispose() {

    }
}
