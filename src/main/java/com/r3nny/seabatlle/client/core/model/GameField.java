package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;


import java.util.LinkedList;
import java.util.List;

import static com.r3nny.seabatlle.client.core.controller.ShipsCreator.shipTypes;
import static com.r3nny.seabatlle.client.core.model.CellStatus.NOT_ALLOWED;
import static com.r3nny.seabatlle.client.core.model.CellStatus.SEA;


public class GameField extends Group {
    public static final int FIELD_SIZE = 10;
    private final float x;
    private final float y;
    private boolean isPlayer;
    private boolean isShipsReady;
    private final Cell[][] field;
    private final List<Ship> ships;

    public GameField(float x, float y, boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.x = x;
        this.y = y;

        field = initCells();
        ships = initShips();
        if (!isPlayer) {
            initAutoShips();
        }

//TODO: Надо будет рандомно. Не работает вообще, епт
        // Game.status = GameStatus.PLAYER_TURN;


    }


    //TODO: Оптимизировать, Сделать так, чтобы когда отпускаешь, люой корабль вернулся на свое место.

    private List<Ship> initShips() {
        List<Ship> ships = new LinkedList<>();
        float startX = this.x;
        float startY = 50;
        for (int i = 0; i < shipTypes.length; i++) {
            Ship ship = new Ship(startX, startY, null, ShipsCreator.shipTypes[i]);
            ships.add(ship);
            ship.setStartX(startX);
            ship.setStartY(startY);
            startX += ShipsCreator.shipTypes[i].getSize() * Cell.SIZE + 20;

            if (i == 2) {
                startY -= 40;
                startX = this.x;
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
                field[i][j] = new Cell(x + Cell.SIZE * j, y - Cell.SIZE * i, j, i, null, SEA);
                super.addActor(field[i][j]);
            }

        }
        return field;
    }

    public void initAutoShips() {
        for (Ship ship : ships
        ) {
            super.removeActor(ship);
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j].setStatus(SEA);
            }
        }
        ShipsCreator.autoCreateShips(this);
        if (isPlayer || SeaBattle.debug) {
            for (Ship ship : ships
            ) {
                super.addActor(ship);
            }

        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].getStatus() == NOT_ALLOWED) {
                    field[i][j].setStatus(SEA);
                }
            }
        }

        this.isShipsReady = true;
    }


    public boolean isShipsReady() {
        return isShipsReady;
    }

    public void setShipsReady(boolean shipsReady) {
        isShipsReady = shipsReady;
    }

    public Cell[][] getField() {
        return field;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
