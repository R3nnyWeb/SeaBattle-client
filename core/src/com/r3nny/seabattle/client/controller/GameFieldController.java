package com.r3nny.seabattle.client.controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.view.CellView;

public class GameFieldController {


    public Group initCellsView(float startX, float startY){
        Group group = new Group();
        int cellCount = GameField.FIELD_SIZE;
        CellView[][] cellViews = new CellView[cellCount][cellCount];
        for (int i = 0; i <cellViews.length ; i++) {
            for (int j = 0; j < cellViews.length; j++) {
                cellViews[i][j] = new CellView(new Cell(j,i,null),startX + CellView.SIZE * j, startY + CellView.SIZE * i);
                group.addActor(cellViews[i][j]);
            }
        }
        return group;
    }



}
