package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.r3nny.seabattle.client.model.Ship;

public class ShipView extends Actor {
    private Ship ship;
    private float x;
    private float y;
    private ShapeRenderer shape;

    public ShipView(float x, float y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
        shape = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(x +2 , y + 2, ship.getType().getSize() * CellView.SIZE - 4, CellView.SIZE -4);
        shape.end();
    }
}
