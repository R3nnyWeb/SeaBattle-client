package com.r3nny.seabattle.client.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabattle.client.controller.ShipsCreator;

import java.util.LinkedList;
import java.util.List;

import static com.r3nny.seabattle.client.controller.ShipsCreator.shipTypes;

public class GameField extends Group {
    public static final int FIELD_SIZE = 10;
    private final float x;
    private final float y;
    private final Cell[][] field;
    private final List<Ship> ships;

    public GameField(float x, float y) {

        this.x = x;
        this.y = y;

        field = initCells();
        ships = initShips();


    }

    //TODO: Оптимизировать, Сделать так, чтобы когда отпускаешь, люой корабль вернулся на свое место.

    private List<Ship> initShips() {
        List<Ship> ships = new LinkedList<>();
        float startX = 100;
        float startY = 100;
        for (int i = 0; i < shipTypes.length; i++) {
            Ship ship = new Ship(startX, startY, null, ShipsCreator.shipTypes[i]);
            ships.add(ship);
            ship.setStartX(startX);
            ship.setStartY(startY);
            startX += ShipsCreator.shipTypes[i].getSize() * Cell.SIZE + 20;

            if (i == 2) {
                startY -= 30;
                startX = 100;
            }
        }
        for (Ship ship : ships
        ) {
            super.addActor(ship);

        }

        return ships;
    }

    private Cell[][] initCells() {
        Cell[][] field = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(x + Cell.SIZE * j, y - Cell.SIZE * i, j, i, null, CellStatus.SEA);
                super.addActor(field[i][j]);
            }

        }
        return field;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
    }

    @Override
    protected void drawDebugChildren(ShapeRenderer shapes) {
        super.drawDebugChildren(shapes);
    }

    public Cell[][] getField() {
        return field;
    }
}
