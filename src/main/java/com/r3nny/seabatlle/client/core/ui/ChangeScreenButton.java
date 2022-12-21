package com.r3nny.seabatlle.client.core.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.r3nny.seabatlle.client.core.StarBattle;

public class ChangeScreenButton extends TextButton {


    public ChangeScreenButton(String text, Runnable withFadeOut, Runnable afterFadeOut) {
        super(text, StarBattle.assetsManager.getMenuButtonSkin());
        super.addAction(StarBattle.animationManager.getFadeInAnimation());
        super.setSize(300, 50);
        super.addListener(new ClickListener() {
            @Override
            public void enter(
                    InputEvent event, float x, float y, int pointer, Actor fromActor) {
                StarBattle.soundManager.playFocusButton();
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                StarBattle.soundManager.playClickSound();
                withFadeOut.run();
                ChangeScreenButton.super.addAction(
                        Actions.sequence(
                                Actions.fadeOut(0.5F),
                        Actions.run(afterFadeOut)));
            }
        });
    }

    public void removeWithFade() {
        super.addAction(StarBattle.animationManager.getFadeOutAnimation());
    }


}
