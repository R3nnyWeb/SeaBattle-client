package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.r3nny.seabatlle.client.core.StarBattle;

import static com.r3nny.seabatlle.client.core.StarBattle.soundManager;

public class EndScreen extends InGameScreen {
    public EndScreen(boolean isPlayerWin) {
        super(new Image(StarBattle.assetsManager.getMenuBackground()));

        if (isPlayerWin)
            soundManager().playWonMusic();
        else
            soundManager.playLoseMusic();

        Texture texture = isPlayerWin ? assetManager().getWonImage() : assetManager().getLoseImage();
        //    private final ShipManager shipsLeft;
        Image result = new Image(texture);
        result.setSize((float)texture.getWidth()/2, (float)texture.getHeight()/2);
        result.setPosition(StarBattle.WORLD_WIDTH/2 - result.getWidth()/2 ,StarBattle.WORLD_HEIGHT/2 - result.getHeight()/2);

        stage.addActor(result);
    }



    @Override
    public void show() {

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
