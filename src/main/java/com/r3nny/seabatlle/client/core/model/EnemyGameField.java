package com.r3nny.seabatlle.client.core.model;

public class EnemyGameField extends GameField{
    public EnemyGameField(float x, float y) {
        super(x, y);
        createShipsAutomaticaly();
        super.setShipsReady(true);
    }
}
