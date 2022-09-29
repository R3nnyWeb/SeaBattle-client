package com.r3nny.seabattle.client.controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.view.CellView;
import com.r3nny.seabattle.client.view.GameFieldView;

public class GameFieldController {
    private GameField gameField;
    private GameFieldView gameFieldView;

    public GameFieldController(GameField gameField, GameFieldView gameFieldView) {
        this.gameField = gameField;
        this.gameFieldView = gameFieldView;
    }


}
