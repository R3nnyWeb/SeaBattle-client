package com.r3nny.seabattle.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.LinkedList;
import java.util.List;

public class GameField extends Group{
    public static final int FIELD_SIZE = 10;
    private Cell[][] field;
    private List<Ship> ships;

    public GameField(float x, float y) {
        ships = new LinkedList<>();
        super.setX(x);
        super.setY(y);
        field = new Cell[FIELD_SIZE][FIELD_SIZE];
        initCells();
    }

    private void initCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(getX() + Cell.SIZE * j,getY() - Cell.SIZE * i,j,i,null,CellStatus.SEA);
                super.addActor(field[i][j]);
            }

        }
    }



}
