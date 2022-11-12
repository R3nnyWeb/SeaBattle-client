package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.controller.CellsController;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;

import java.util.Arrays;


public class Ship extends Actor {
    private final ShipType type;
    private final ShapeRenderer shape;
    private Cell[] cells;
    private float startX;
    private float startY;
    private boolean isVertical;
    private Sprite texture;

    private boolean isKilled;




    public Ship(float x, float y, Cell[] cells, ShipType type) {

        shape = new ShapeRenderer();


        this.cells = cells;
        this.type = type;
        super.setX(x);
        super.setY(y);
        //TODO: Кейс сделай, балбес
        texture = new Sprite(SeaBattle.assetsManager.getOneDeckShip());
        if (type == ShipType.FOUR_DECK) {
            texture = new Sprite(SeaBattle.assetsManager.getFourDeckShip());
        }
        if (type == ShipType.THREE_DECK) {
            texture = new Sprite(SeaBattle.assetsManager.getThreeDeckShip());
        }

        updateBounds();
        this.addListener(new InputListener() {

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                SeaBattle.soundManager.playFocusButton();
            }


            //TODO: При отпускании ивенте на пкм срабатывает touchUp надо исправить
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if ((Ship.this.cells == null)) {
                    if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                        SeaBattle.soundManager.playClickSound();
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
                        if (ShipsCreator.addShipToGameField(currentCell, Ship.this, Game.playerField)) {
                            ShipsCreator.createdPlayerShips++;

                            //TODO: Разные звуки для разных типов
                            SeaBattle.soundManager.playShipEnterSound();
                            Gdx.app.log("Ship ent", "Ss");
                        }
                        Game.playerField.setShipsReady(ShipsCreator.createdPlayerShips == ShipsCreator.shipTypes.length);
                    }
                }
                updateBounds();


            }


        });
    }





    //TODO: private?? Ship.this.updateBounds()
    public void updateBounds() {
        if (isVertical) {

            this.setBounds(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        } else {
            this.setBounds(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }

    }

    public boolean isKilled() {
        return isKilled;
    }

    public void kill(){
        this.isKilled = true;
        for (Cell c : cells) {
            c.setStatus(CellStatus.KILLED);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


//        if (this.selected) {
//            shape.setProjectionMatrix(batch.getProjectionMatrix());
//            shape.begin(ShapeRenderer.ShapeType.Line);
//
//            shape.setColor(Color.WHITE);
//            if (isVertical) {
//                shape.rect(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
//
//            } else {
//                shape.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
//            }
//            shape.end();
//        }


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

    public ShapeRenderer getShape() {
        return shape;
    }

    public Cell[] getCells() {
        return cells;
    }

    public Sprite getTexture() {
        return texture;
    }

    public void setTexture(Sprite texture) {
        this.texture = texture;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "type=" + type +
                ", cells=" + Arrays.toString(cells) +
                ", isVertical=" + isVertical +
                '}';
    }
}
