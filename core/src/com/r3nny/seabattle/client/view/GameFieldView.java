package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.GameField;

public class GameFieldView extends Group {

    private GameField gameField;

    private  CellView[][] fieldView;
    private float x;
    private float y;


    public GameFieldView(float x, float y) {
        this.gameField = new GameField();
        this.x = x;
        this.y = y;
        fieldView = new CellView[GameField.FIELD_SIZE][GameField.FIELD_SIZE];
        initCellsView();

    }

    private void initCellsView() {
        for (int i = 0; i < fieldView.length; i++) {
            for (int j = 0; j < fieldView.length; j++) {
                fieldView[i][j] = new CellView(gameField.getField()[i][j],x + CellView.SIZE * j, x + CellView.SIZE * i);
                super.addActor(fieldView[i][j]);
            }

        }
    }
}
