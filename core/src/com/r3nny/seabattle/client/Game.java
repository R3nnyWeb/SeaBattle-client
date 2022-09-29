package com.r3nny.seabattle.client;

import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.view.GameFieldView;
import jdk.jshell.Snippet;

public class Game {
    public  static GameStatus status = GameStatus.SHIPS_STAGE;
    public static GameField playerField;
    public static GameFieldView playerView;
    public static GameField enemy;
}
