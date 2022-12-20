/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.controller.GameController;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.GameField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class SingleGame extends Game {

    private final float BOT_THINKING_TIME = 1.3F;
    private float time = 0F;

    private final List<Cell> cellsToShoot;

    private boolean isNeedToKill = false;

    private Stack<Cell> possibleCellsToShoot;

    private final List<Cell> hittedCells;

    public SingleGame() {
        playerField = new GameField(PLAYER_FIELD_X, FIELD_Y, true);
        enemy = new GameField(ENEMY_FIELD_X, FIELD_Y, false);
        this.hittedCells = new ArrayList<>();

        cellsToShoot = new ArrayList<>();
        Cell[][] field = playerField.getField();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                cellsToShoot.add(field[i][j]);
            }
        }
    }

    public boolean isShipsReady() {
        return playerField.isShipsReady() && enemy.isShipsReady();
    }

    // TODO: Переписать
    public void update() {
        if (Game.status == GameStatus.ENEMY_TURN) {
            time += Gdx.graphics.getDeltaTime();
        }
        if (Game.status == GameStatus.ENEMY_TURN && time > BOT_THINKING_TIME) {
            if (isNeedToKill) {

                if (possibleCellsToShoot.empty()) {
                    possibleCellsToShoot = getPossibleCellsToShoot(hittedCells.get(0));
                } else {
                    Cell cell = possibleCellsToShoot.pop();
                     GameController.shoot(cell.getRow(), cell.getColumn());
                    if (cell.isKilled()) {
                        isNeedToKill = false;
                        possibleCellsToShoot.clear();
                        hittedCells.clear();
                    } else if (cell.isInjured()) {
                        hittedCells.add(cell);
                        possibleCellsToShoot = getPossibleCellsToShoot(cell);
                    }
                    time = 0f;
                    cellsToShoot.remove(cell);
                }
            } else {
                Random rd = new Random();
                Cell cell = cellsToShoot.get(rd.nextInt(cellsToShoot.size() - 1));
                if(!cell.isMissed() && !cell.isKilled()) {
                    GameController.shoot(cell.getRow(), cell.getColumn());
                    if (cell.isInjured()) {
                        isNeedToKill = true;
                        hittedCells.add(cell);
                        possibleCellsToShoot = getPossibleCellsToShoot(cell);
                    }
                    ;
                    time = 0f;
                }
                cellsToShoot.remove(cell);
            }
        }
    }

    private void pushVerticalCells(int row, int column, Cell[][] field, Stack<Cell> stack) {
        if (row - 1 >= 0
                && (!field[row - 1][column].isInjured()
                        && !field[row - 1][column].isMissed())) {
            stack.push(field[row - 1][column]);
        }
        if (row + 1 < field.length
                && (!field[row + 1][column].isInjured()
                        && !field[row + 1][column].isMissed())) {
            stack.push(field[row + 1][column]);
        }
    }

    private void pushHorizontalCells(int row, int column, Cell[][] field, Stack<Cell> stack) {
        if (column - 1 >= 0
                && (!field[row][column - 1].isInjured()
                        && !field[row][column - 1].isMissed())) {
            stack.push(field[row][column - 1]);
        }
        if (column + 1 < field.length
                && (!field[row][column + 1].isInjured()
                        && !field[row][column + 1].isMissed())) {
            stack.push(field[row][column + 1]);
        }
    }

    private Stack<Cell> getPossibleCellsToShoot(Cell cell) {
        Stack<Cell> stack = new Stack<Cell>();
        int row = cell.getRow();
        int column = cell.getColumn();
        Cell[][] field = playerField.getField();
        // TODO: Переработать
        if (cell.getShip().isVertical()) {
            pushHorizontalCells(row, column, field, stack);
            pushVerticalCells(row, column, field, stack);
        } else {
            pushVerticalCells(row, column, field, stack);
            pushHorizontalCells(row, column, field, stack);
        }

        return stack;
    }
}
