package com.r3nny.seabattle.client.model;

import com.r3nny.seabattle.client.Ship;

public class Cell {

    private int fieldX;
    private int fieldY;
    private Ship ship;


    public Cell(int fieldX, int fieldY, Ship ship) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.ship = ship;
    }

    @Override
    public String toString() {
        return "CellModel{" +
                "fieldX=" + fieldX +
                ", fieldY=" + fieldY +
                '}';
    }
}
