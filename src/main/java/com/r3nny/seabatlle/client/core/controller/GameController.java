/* (C)2022 */
package com.r3nny.seabatlle.client.core.controller;

import static com.r3nny.seabatlle.client.core.Game.playerField;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.CellStatus;

public class GameController {

    private static boolean isKilling(Cell cell) {
        var ship = cell.getShip();
        Cell[] cells = ship.getCells();
        for (Cell c : cells) {
            if (c.getStatus() == CellStatus.HEALTHY) {
                return false;
            }
        }
        ship.kill();
        if (Game.status == GameStatus.PLAYER_TURN) {
            Game.enemy.addActor(ship);
        }
        return true;
    }

    public static CellStatus shoot(int row, int column) {
        Gdx.app.log(Game.status.toString(), row + " " + column + "");

        // TODO : REWORK

        Cell cell;
        if (Game.status == GameStatus.PLAYER_TURN) {
            cell = Game.enemy.getField()[row][column];
        } else {
            cell = playerField.getField()[row][column];
        }

        if (cell.getStatus() == CellStatus.SEA) {
            if (Game.status == GameStatus.PLAYER_TURN) {
                Game.status = GameStatus.ENEMY_TURN;
            } else {
                Game.status = GameStatus.PLAYER_TURN;
            }
            SeaBattle.soundManager.playMissSound();
            cell.setStatus(CellStatus.MISS);
        }
        if (cell.getStatus() == CellStatus.HEALTHY) {

            cell.setStatus(CellStatus.INJURED);
            // TODO: Проверка на убийство
            if (isKilling(cell)) {
                SeaBattle.soundManager.playKilledSound();
            } else {
                SeaBattle.soundManager.playInjuredSound();
            }
        }

        return cell.getStatus();
    }
}
