/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.model.Ship;

public class AnimationSpritesManager {

    // TODO: Вынести все Actions сюда
    private final Animation<TextureRegion> injuredAnimation;
    private final Animation<TextureRegion> burningAnimation;
    private final Animation<TextureRegion> missAnimation;
    private final Animation<TextureRegion> shipDestroyingAnimation;
    private final Animation<TextureRegion> explosionAnimation;

    public AnimationSpritesManager() {
        this.injuredAnimation =
                loadAnimation(StarBattle.assetsManager.getInjuredAnimation(), 2, 5, 0.07F);
        this.burningAnimation =
                loadAnimation(StarBattle.assetsManager.getBurningAnimation(), 2, 5, 0.07F);
        this.missAnimation =
                loadAnimation(StarBattle.assetsManager.getMissAnimation(), 4, 5, 0.05F);
        this.shipDestroyingAnimation =
                loadAnimation(StarBattle.assetsManager.getShipDestroyingAnimation(), 2, 4, 0.1F);
        this.explosionAnimation =
                loadAnimation(StarBattle.assetsManager.getExplosionAnimation(), 13, 1, 0.07f);
    }

    private Animation<TextureRegion> loadAnimation(
            Texture sheet, int rows, int cols, float duration) {
        TextureRegion[][] tmp =
                TextureRegion.split(sheet, sheet.getWidth() / cols, sheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<>(duration, frames);
    }

    public Animation<TextureRegion> getInjuredAnimation() {
        return injuredAnimation;
    }

    public Animation<TextureRegion> getBurningAnimation() {
        return burningAnimation;
    }

    public Animation<TextureRegion> getMissAnimation() {
        return missAnimation;
    }

    public Animation<TextureRegion> getShipDestroyingAnimation() {
        return shipDestroyingAnimation;
    }

    public Animation<TextureRegion> getExplosionAnimation() {
        return explosionAnimation;
    }

    public Action getShipEnterAction(Ship ship, Runnable run) {
        SequenceAction sequence;
        if (!ship.isVertical()) {
            sequence =
                    Actions.sequence(
                            Actions.moveTo(ship.getX() - 40, ship.getY()),
                            Actions.moveTo(ship.getX(), ship.getY(), 1F));
        } else {
            sequence =
                    Actions.sequence(
                            Actions.moveTo(ship.getX(), ship.getY() + 40),
                            Actions.moveTo(ship.getX(), ship.getY(), 1F));
        }
        return Actions.sequence(
                Actions.parallel(sequence, Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F))),
                Actions.run(run));
    }
}
