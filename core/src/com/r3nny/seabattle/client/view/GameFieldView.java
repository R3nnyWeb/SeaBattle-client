package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.model.Ship;

import java.util.LinkedList;
import java.util.List;

public class GameFieldView extends Group {

    private GameField gameField;
    private final CellView[][] fieldView;
    private final List<ShipView> ships;
    private ShipView shipPreview;

    private final float x;
    private final float y;


    public GameFieldView(float x, float y, GameField gameField) {
        this.gameField = gameField;
        ships = new LinkedList<>();
        this.x = x;
        this.y = y;
        fieldView = new CellView[GameField.FIELD_SIZE][GameField.FIELD_SIZE];
        initCellsView();

    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    private void initCellsView() {
        for (int i = 0; i < fieldView.length; i++) {
            for (int j = 0; j < fieldView.length; j++) {
                fieldView[i][j] = new CellView(gameField.getField()[i][j], x + CellView.SIZE * j, y - CellView.SIZE * i);
                super.addActor(fieldView[i][j]);
            }

        }
    }




    public void addShipView(Ship ship) {
        ShipView shipV = new ShipView(x + ship.getCells()[0].getFieldX() * CellView.SIZE, y - ship.getCells()[0].getFieldY() * CellView.SIZE, ship);
        ships.add(shipV);
        super.addActor(shipV);
    }

    public void addPreViewShip(Ship ship) {
        removeShipPreview();
        shipPreview = new ShipView(x + ship.getCells()[0].getFieldX() * CellView.SIZE, y - ship.getCells()[0].getFieldY() * CellView.SIZE, ship);
        super.addActor(shipPreview);

    }


    public void removeShipPreview() {
        super.removeActor(shipPreview);
    }
}

