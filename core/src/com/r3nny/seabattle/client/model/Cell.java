package com.r3nny.seabattle.client.model;

public class Cell {

    private final int fieldX;
    private final int fieldY;
    private Ship ship;

    private CellStatus status;

    public int getFieldX() {
        return fieldX;
    }

    public int getFieldY() {
        return fieldY;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Cell(int fieldX, int fieldY, Ship ship) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.ship = ship;
        status = CellStatus.SEA;
    }



    @Override
    public String toString() {
        return "Cell{" +
                "fieldX=" + fieldX +
                ", fieldY=" + fieldY +
                ", status=" + status +
                '}';
    }
}
