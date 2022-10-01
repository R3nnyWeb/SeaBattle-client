package com.r3nny.seabattle.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.controller.CellsController;
import com.r3nny.seabattle.client.controller.ShipsCreator;

public class Ship extends Actor {
    private final ShipType type;
    private final ShapeRenderer shape;
    private Cell[] cells;
    private float startX;
    private float startY;
    private boolean isVertical;


    public Ship(float x, float y, Cell[] cells, ShipType type) {
        this.cells = cells;
        this.type = type;
        super.setX(x);
        super.setY(y);

        shape = new ShapeRenderer();
        updateBounds();

        this.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Ship.this.cells == null){
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                        setVertical(!isVertical());
                    }
                    updateBounds();

                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (Ship.this.cells == null) {
                    setX(event.getStageX() - 10);
                    setY(event.getStageY() - 10);


                    updateBounds();
                }


            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Ship.this.cells == null) {
                    Cell currentCell = CellsController.getCellByCoord(event.getStageX() - 5, event.getStageY() - 5);
                    setX(getStartX());
                    setY(getStartY());
                    if (currentCell != null) {
                        System.out.println( ShipsCreator.createShip(currentCell, Ship.this));
                    }
                }


            }


        });
    }

    private void updateBounds() {
        if(isVertical){
            this.setBounds(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        } else {
            this.setBounds(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.NAVY);
        if (isVertical) {
            shape.rect(getX(), getY(), Cell.SIZE,type.getSize() * Cell.SIZE);

        } else {
            shape.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }


        shape.end();
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.setColor(Color.RED);
        if (isVertical) {
            shapes.rect(getX(), getY(), Cell.SIZE,type.getSize() * Cell.SIZE);

        } else {
            shapes.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }


    }

    public float getStartX() {
        return startX;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public ShipType getType() {
        return type;
    }


    public void setCells(Cell[] cells) {
        this.cells = cells;
    }
}
