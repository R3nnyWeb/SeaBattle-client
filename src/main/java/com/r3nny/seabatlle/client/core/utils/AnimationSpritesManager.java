package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.model.Ship;

public class AnimationSpritesManager {

    private final Animation injuredAnimation;
    private final Animation burningAnimation;
    private final Animation missAnimation;


    public AnimationSpritesManager() {
        this.injuredAnimation = loadAnimation(SeaBattle.assetsManager.getInjuredAnimation(),2,5,0.07F);
        this.burningAnimation = loadAnimation(SeaBattle.assetsManager.getBurningAnimation(),2,5,0.07F);
        this.missAnimation = loadAnimation(SeaBattle.assetsManager.getMissAnimation(),4,5,0.05F);
    }

    private Animation loadAnimation(Texture sheet, int rows, int cols, float duration) {
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / cols, sheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation(duration, frames);
    }

    public Animation getInjuredAnimation(){
        return  injuredAnimation;
    }

    public Animation getBurningAnimation() {
        return burningAnimation;
    }
    public Animation getMissAnimation(){
        return missAnimation;
    }

    public Action getShipEnterAction(Ship ship, Runnable run) {
        SequenceAction sequence;
        if (!ship.isVertical()) {
            sequence = Actions.sequence(
                    Actions.moveTo(ship.getX() - 40, ship.getY()),
                    Actions.moveTo(ship.getX(), ship.getY(), 1F));
        } else {
            sequence = Actions.sequence(
                    Actions.moveTo(ship.getX(), ship.getY() + 40),
                    Actions.moveTo(ship.getX(), ship.getY(), 1F));
        }
        Action action = Actions.sequence(
                Actions.parallel(
                        sequence,
                        Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F))),
                Actions.run(run));
        return action;
    }
}
