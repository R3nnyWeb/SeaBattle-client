/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.controller;

import static com.r3nny.seabatlle.client.core.game.Game.enemy;
import static com.r3nny.seabatlle.client.core.game.Game.player;

import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.actors.Ship;
import java.util.List;


/**Содержит единственный метод для обработки выстрела*/
public class GameController {

    /**Меняет состояния клетки на поле в зависимости от результата и хода*/
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
                : Game.player.getField()[row][column];
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
        List<Cell> cells = ship.getCells();
        return cells.stream().filter(Cell::isHealthy).toList().isEmpty();
    }

    private static void makeKilled(Cell cell) {
        Ship ship = cell.getShip();
        if (isPlayerTurn())
            enemy.killShip(ship);
        else
            player.killShip(ship);
        StarBattle.soundManager.playKilledSound();
    }
}
