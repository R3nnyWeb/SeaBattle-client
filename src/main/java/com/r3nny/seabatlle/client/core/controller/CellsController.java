package com.r3nny.seabatlle.client.core.controller;


import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.model.Cell;


public class CellsController {


    public static Cell getCellByCoord(float x, float y) {
        Cell[][] field = Game.playerField.getField();
        for (Cell[] cells : field) {
            for (int j = 0; j < field.length; j++) {
                Cell currentCell = cells[j];
                float startX = cells[j].getX();
                float startY = cells[j].getY();
                float endX = startX + cells[j].getWidth();
                float endY = startY + cells[j].getHeight();
                if ((x > startX) && (x < endX) && (y > startY) && (y < endY)) {
                    return currentCell;
                }
            }
        }
        return null;
    }
}
