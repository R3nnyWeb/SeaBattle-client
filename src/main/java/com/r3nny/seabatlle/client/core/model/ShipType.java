package com.r3nny.seabatlle.client.core.model;

public enum ShipType {

    ONE_DECK(1),
    TWO_DECK(2),
    THREE_DECK(3),
    FOUR_DECK(4);
   private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
