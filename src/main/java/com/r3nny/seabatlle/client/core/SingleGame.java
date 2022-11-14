/* (C)2022 */
package com.r3nny.seabatlle.client.core;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.controller.GameController;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.CellStatus;
import com.r3nny.seabatlle.client.core.model.GameField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleGame extends Game {

    private final float BOT_THINKING_TIME = 1.3F;
    private float time = 0F;

    private List<Cell> cellsToShoot;

    public SingleGame() {
        playerField = new GameField(PLAYER_FIELD_X, FIELD_Y, true);
        enemy = new GameField(ENEMY_FIELD_X, FIELD_Y, false);
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

    public void update() {
        if (Game.status == GameStatus.ENEMY_TURN) {
            time += Gdx.graphics.getDeltaTime();
        }
        if (Game.status == GameStatus.ENEMY_TURN && time > BOT_THINKING_TIME) {
            Random rd = new Random();
            Cell cell = cellsToShoot.get(rd.nextInt(cellsToShoot.size() - 1));
            if (cell.getStatus() != CellStatus.MISS && cell.getStatus() != CellStatus.KILLED) {
                GameController.shoot(cell.getRow(), cell.getColumn());
                time = 0f;
            }
            cellsToShoot.remove(cell);
        }
    }
}
