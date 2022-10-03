package com.r3nny.seabattle.client.controller;

import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.CellStatus;

public class GameController {

    public static CellStatus shoot(Cell cell){
        if(cell.getStatus() == CellStatus.SEA){
            cell.setStatus(CellStatus.NOT_ALLOWED);
//            Game.status = GameStatus.ENEMY_TURN;
            return CellStatus.MISS;

        }
        if(cell.getStatus() == CellStatus.HEALTHY){
            Game.enemy.addActor( cell.getShip());
            return CellStatus.INJURED;
        }

        return CellStatus.SEA;
    }
}
