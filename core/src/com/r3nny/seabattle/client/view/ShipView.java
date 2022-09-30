package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.model.Cell;
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
        updateBounds();

        this.addListener(new InputListener(){



            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                updateBounds();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setX(event.getStageX()-10);
                setY(event.getStageY()-10);

                updateBounds();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Upped");
            }
        });
    }

    private void updateBounds() {
        this.setBounds(x,y,ship.getType().getSize()* Cell.SIZE, Cell.SIZE);
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.NAVY);
        shape.rect(x , y, ship.getType().getSize() * Cell.SIZE, Cell.SIZE);
        shape.end();
    }
}
