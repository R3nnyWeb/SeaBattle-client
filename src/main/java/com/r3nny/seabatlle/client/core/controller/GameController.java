/* (C)2022 */
package com.r3nny.seabatlle.client.core.controller;

import static com.r3nny.seabatlle.client.core.Game.enemy;
import static com.r3nny.seabatlle.client.core.Game.playerField;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.CellStatus;
import com.r3nny.seabatlle.client.core.model.Ship;

public class GameController {

    private static boolean isKilling(Cell cell) {
        Ship ship = cell.getShip();
        Cell[] cells = ship.getCells();
        for (Cell c : cells) {
            if (c.getStatus() == CellStatus.HEALTHY) {
                return false;
            }
        }
        ship.kill();
        if (Game.status == GameStatus.ENEMY_TURN) {
            playerField.decByShipType(ship.getType());
            ShipsCreator.changeCellsStatusAroundShip(ship, playerField.getField(), CellStatus.MISS);
            playerField.removeActor(ship);
        } else {
            enemy.decByShipType(ship.getType());
            ShipsCreator.changeCellsStatusAroundShip(ship, enemy.getField(), CellStatus.MISS);
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
            StarBattle.soundManager.playMissSound();
            cell.setStatus(CellStatus.MISS);
        }
        if (cell.getStatus() == CellStatus.HEALTHY) {
            cell.setStatus(CellStatus.INJURED);
            if (isKilling(cell)) {
                StarBattle.soundManager.playKilledSound();
            } else {
                StarBattle.soundManager.playInjuredSound();
            }
        }

        return cell.getStatus();
    }
}
