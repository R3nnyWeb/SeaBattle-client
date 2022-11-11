package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.utils.Assets;

public class SplashScreen implements Screen {

    private Texture splashTexture;
    private Image splashImage;
    private SeaBattle game;
    private Assets manager;
    private boolean startLoading = false;

    @Override
    public void show() {
        manager = new Assets();
        game = ((SeaBattle) Gdx.app.getApplicationListener());
        splashTexture = new Texture(Gdx.files.internal("splashLogo.png"));
        splashImage = new Image(splashTexture);
        splashImage.setWidth(600);
        splashImage.setX(SeaBattle.WORLD_WIDTH/2 - 600/2);
        splashImage.setY(SeaBattle.WORLD_HEIGHT/2 - splashImage.getHeight()/2);
        splashImage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(1.25F), Actions.delay(1F), Actions.run(new Runnable() {
            @Override
            public void run() {
                manager.loadAllAssets();
                startLoading = true;
            }
        })));

        game.stage.addActor(splashImage);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.stage.act();
        game.stage.draw();
        if (manager.update() && startLoading) {
            game.stage.addAction(Actions.sequence( Actions.fadeOut(0.5F), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.stage.clear();
                    game.setScreen(new MenuScreen());
                }
            })));

        }

    }

    @Override
    public void resize(int width, int height) {
        game.stage.getViewport().update(width, height, true);
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
