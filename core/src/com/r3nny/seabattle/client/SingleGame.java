package com.r3nny.seabattle.client;

import com.r3nny.seabattle.client.model.GameField;

public class SingleGame extends Game{

    public SingleGame() {
       playerField = new GameField(PLAYER_FIELD_X, FIELD_Y, true);
       enemy = new GameField(ENEMY_FIELD_X, FIELD_Y, false);

    }
}
