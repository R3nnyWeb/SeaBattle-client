package com.r3nny.seabatlle.client.core;

import com.r3nny.seabatlle.client.core.model.GameField;


public class SingleGame extends Game{

    public SingleGame() {
       playerField = new GameField(PLAYER_FIELD_X, FIELD_Y, true);
       enemy = new GameField(ENEMY_FIELD_X, FIELD_Y, false);

    }

    public  boolean isShipsReady(){
        return playerField.isShipsReady() && enemy.isShipsReady();
    }

}
