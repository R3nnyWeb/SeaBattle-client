/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.controller;

import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.Ship;

import static com.r3nny.seabatlle.client.core.Game.enemy;
import static com.r3nny.seabatlle.client.core.Game.playerField;

public class GameController {

    public static void shoot(int row, int column) {
        Cell cell = getHittedCell(row, column);
        if (isShotMissed(cell)) {
            changeGameTurn();
            makeMiss(cell);
        } else {
            if (isShotHitted(cell)) {
                makeInured(cell);
                if (isKilling(cell)) makeKilled(cell);
            }
        }
    }

    private static Cell getHittedCell(int row, int column) {
        return isPlayerTurn()
                ? Game.enemy.getField()[row][column]
                : Game.playerField.getField()[row][column];
    }

    private static boolean isPlayerTurn() {
        return Game.status == GameStatus.PLAYER_TURN;
    }

    private static boolean isShotMissed(Cell cell) {
        return cell.isSea();
    }

    private static void changeGameTurn() {
        if (isPlayerTurn()) setEnemyTurn();
        else setPlayerTurn();
    }

    private static void setEnemyTurn() {
        Game.status = GameStatus.ENEMY_TURN;
    }

    private static void setPlayerTurn() {
        Game.status = GameStatus.PLAYER_TURN;
    }

    private static void makeMiss(Cell cell) {
        StarBattle.soundManager.playMissSound();
        cell.setMiss();
    }

    private static boolean isShotHitted(Cell cell) {
        return cell.isHealthy();
    }

    private static void makeInured(Cell cell) {
        cell.setInjured();
        StarBattle.soundManager.playInjuredSound();
    }

    private static boolean isKilling(Cell cell) {
        Ship ship = cell.getShip();
        Cell[] cells = ship.getCells();
        for (Cell c : cells) {
            if (c.isHealthy()) {
                return false;
            }
        }
        return true;
    }

    private static void makeKilled(Cell cell) {
        Ship ship = cell.getShip();
        if (isPlayerTurn()) enemy.killShip(ship);
        else playerField.killShip(ship);
        StarBattle.soundManager.playKilledSound();
    }
}
