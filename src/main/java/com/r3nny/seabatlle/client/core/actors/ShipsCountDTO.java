/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.r3nny.seabatlle.client.core.StarBattle;

/**Описывает тип корабля и количество оставшихся*/
public class ShipsCountDTO extends Actor {

    private final ShipType type;
    private final Sprite texture;
    private int count;
    BitmapFont font;

    public ShipsCountDTO(float x, float y, ShipType type, int count) {
        super.setX(x);
        super.setY(y);
        this.type = type;
        this.count = count;
        switch (type) {
            case ONE_DECK -> texture = new Sprite(StarBattle.assetsManager.getOneDeckShip());
            case TWO_DECK -> texture = new Sprite(StarBattle.assetsManager.getTwoDeckShip());
            case THREE_DECK -> texture = new Sprite(StarBattle.assetsManager.getThreeDeckShip());
            default -> texture = new Sprite(StarBattle.assetsManager.getFourDeckShip());
        }
        texture.setSize(Cell.SIZE / 1.3F * type.getSize(), Cell.SIZE / 1.3F);
        texture.setX(getX());
        texture.setY(getY());
        font = StarBattle.assetsManager.getFont(20);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        texture.draw(batch);
        font.draw(
                batch, String.valueOf(count), getX() - 20, getY() + (texture.getHeight() * 0.75F));
    }

    public ShipType getType() {
        return type;
    }

    public void dec() {
        count--;
    }

    public int getCount() {
        return count;
    }
}
