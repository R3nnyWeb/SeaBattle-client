package com.r3nny.seabattle.client;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;

//TODO: Rework
@Deprecated
public class GameField  {
    private final int CELL_COUNT = 10;
    private final int CELL_SIZE = 25;
    private float x;
    private float y;
    private Cell[][] field;

    public GameField(float x, float y) {
        this.x = x;
        this.y = y;
        field = new Cell[CELL_COUNT][CELL_COUNT];
        initField();

    }

    public Group initField() {
        Group group = new Group();
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                field[i][j] = new Cell(x + CELL_SIZE * j, y + CELL_SIZE * i, CELL_SIZE, j , i, null);
                group.addActor(field[i][j]);


            }
        }
        return group;
    }





}
