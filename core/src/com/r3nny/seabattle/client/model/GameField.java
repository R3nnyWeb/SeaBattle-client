package com.r3nny.seabattle.client.model;

import java.util.LinkedList;
import java.util.List;

public class GameField {
    public static final int FIELD_SIZE = 10;
    private Cell[][] field;
    private List<Ship> ships;

    public GameField() {
        ships = new LinkedList<>();
        field = new Cell[FIELD_SIZE][FIELD_SIZE];
        initCells();
    }

    private void initCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(j,i,null);
            }

        }
    }

    public void addShip(Ship ship) {
         addShip(ship);

    }

    public List<Ship> getShips() {
        return ships;
    }

    public Cell[][] getField() {
        return field;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
