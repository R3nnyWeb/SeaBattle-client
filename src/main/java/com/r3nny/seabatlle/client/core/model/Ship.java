package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.controller.CellsController;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;


public class Ship extends Actor {
    private final ShipType type;
    private final ShapeRenderer shape;
    private Cell[] cells;
    private float startX;
    private float startY;
    private boolean isVertical;
    private Sprite texture;


    public Ship(float x, float y, Cell[] cells, ShipType type) {
        this.cells = cells;
        this.type = type;
        super.setX(x);
        super.setY(y);
        //TODO: Кейс сделай, балбес
        texture = new Sprite(new Texture("1xShip.png"));
        if (type == ShipType.FOUR_DECK) {
            texture = new Sprite(new Texture("4xShip.png"));
        }
        if (type == ShipType.THREE_DECK) {
            texture = new Sprite(new Texture("3xShip.png"));
        }


        shape = new ShapeRenderer();
        updateBounds();

        this.addListener(new InputListener() {

            //TODO: При отпускании ивенте на пкм срабатывает touchUp надо исправить
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ((Ship.this.cells == null)) {
                    if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                        setVertical(!isVertical());
                    }
                    updateBounds();
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    if (Ship.this.cells == null) {
                        setX(event.getStageX() - 10);
                        setY(event.getStageY() - 10);
                        updateBounds();
                    }
                }


            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                if (Ship.this.cells == null) {
                    Cell currentCell = CellsController.getCellByCoord(event.getStageX() - 5, event.getStageY() - 5);
                    setX(getStartX());
                    setY(getStartY());
                    if (currentCell != null) {
                        System.out.println(ShipsCreator.createShip(currentCell, Ship.this, Game.playerField));
                    }
                }
                updateBounds();


            }


        });
    }

    private void updateBounds() {
        if (isVertical) {

            this.setBounds(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        } else {
            this.setBounds(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        if (isVertical) {
            texture.setPosition(getX(), getY());
            texture.setSize(Cell.SIZE, type.getSize() * Cell.SIZE);
            texture.draw(batch);
//            batch.draw(texture, getX(), getY(), Cell.SIZE, Cell.SIZE);


        } else {
            batch.draw(texture, getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }


    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.setColor(Color.RED);
        if (isVertical) {
            shapes.rect(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);

        } else {
            shapes.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }


    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        texture.rotate90(vertical);
        isVertical = vertical;
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
