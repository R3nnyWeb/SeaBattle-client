package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.ui.ChangeScreenButton;

public abstract class InGameScreen extends Screen{
    protected Image gameLogo;
    protected Image bgImage;
    protected InGameScreen(Image bgImage) {
        this.bgImage = bgImage;
        setUpBgImage();
        setUpGameLogo();
        setUpBackButton();
    }

    private void setUpBgImage() {
        bgImage.setSize(StarBattle.WORLD_WIDTH, StarBattle.WORLD_HEIGHT);

        stage.addActor(bgImage);
    }

    private void setUpGameLogo() {
        Image  gameLogo = new Image(assetManager().getMenuLogo());
        gameLogo.setSize(310, 27);
        gameLogo.setX(StarBattle.WORLD_WIDTH / 2 - gameLogo.getWidth() / 2);
        gameLogo.setY(StarBattle.WORLD_HEIGHT - gameLogo.getHeight() - 20);

        stage.addActor(gameLogo);
    }

    private void setUpBackButton() {
        ChangeScreenButton backButton =
                new ChangeScreenButton("Back to menu", runOnWhenChangingScreen(), () -> {
                    stage.clear();
                    application.setScreen(new MenuScreen());
                });
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(StarBattle.WORLD_HEIGHT - 10 - backButton.getHeight());

        stage.addActor(backButton);
    }

    private Runnable runOnWhenChangingScreen(){
        return () -> stage.addAction(animationManager().getFadeOutAnimation());
    }
}
