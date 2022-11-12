package com.r3nny.seabatlle.client.core;

import com.r3nny.seabatlle.client.core.model.GameField;



public class Game {
    protected static final int FIELD_Y = 426;
    protected static final int PLAYER_FIELD_X = 89;
    protected static final int ENEMY_FIELD_X = 633;


    public  static  GameStatus status = GameStatus.SHIPS_STAGE;
    public static GameField playerField;

    public static GameField enemy;



}
