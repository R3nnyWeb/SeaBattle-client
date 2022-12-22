package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.utils.AnimationManager;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.r3nny.seabatlle.client.core.utils.SoundManager;

public abstract class Screen implements com.badlogic.gdx.Screen {
    protected final StarBattle application;
    protected final Stage stage;



    protected Screen() {
        this.stage = StarBattle.setUpStage();
        this.application = (StarBattle) Gdx.app.getApplicationListener();


    }

    protected AnimationManager animationManager() {
        return StarBattle.animationManager;
    }
    protected Assets assetManager() {
        return StarBattle.assetsManager;
    }
    protected SoundManager soundManager() {
        return StarBattle.soundManager;
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        stage.act();
        stage.draw();
    }
}
