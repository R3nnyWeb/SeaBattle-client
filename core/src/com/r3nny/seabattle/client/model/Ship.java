package com.r3nny.seabattle.client.model;

import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.ShipType;

public class Ship {
    private Cell[] cells;
    private ShipType type;

    public Ship(Cell[] cells, ShipType type) {
        this.cells = cells;
        this.type = type;
    }

    public Cell[] getCells() {
        return cells;
    }

    public ShipType getType() {
        return type;
    }
}
