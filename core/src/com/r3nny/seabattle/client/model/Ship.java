package com.r3nny.seabattle.client.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.controller.ShipsCreator;

public class Ship extends Actor {
    private Cell[] cells;
    private ShipType type;

    private ShapeRenderer shape;



    public Ship(float x, float y, Cell[] cells, ShipType type) {
        this.cells = cells;
        this.type = type;
        super.setX(x);
        super.setY(y);

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
                if(Ship.this.cells == null) {
                    setX(event.getStageX()-10);
                    setY(event.getStageY()-10);
                    updateBounds();
                }

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(Ship.this.cells == null) {
                    Cell currentCell = CellsController.getCellByCoord(event.getStageX() - 5, event.getStageY()-5);



                    if(currentCell != null){
                        if (ShipsCreator.canCreateShipHere(currentCell, Ship.this, Game.playerField.getField())){
                            ShipsCreator.createShip(currentCell,Ship.this);

                        }
                    }
                }


            }


        });
    }

    private void updateBounds() {
        this.setBounds(getX(),getY(),type.getSize()* Cell.SIZE, Cell.SIZE);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.NAVY);
        shape.rect(getX() , getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        shape.end();
    }


    public ShipType getType() {
        return type;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }
}
