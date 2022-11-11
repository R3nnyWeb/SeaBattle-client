package com.r3nny.seabatlle.client.core.controller;

import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.CellStatus;


public class GameController {

    public static CellStatus shoot(Cell cell){
        if(cell.getStatus() == CellStatus.SEA){
            cell.setStatus(CellStatus.NOT_ALLOWED);
            Game.status = GameStatus.ENEMY_TURN;
            return CellStatus.MISS;

        }
        if(cell.getStatus() == CellStatus.HEALTHY){
            //TODO: Проверка на убийство
            return CellStatus.INJURED;
        }


        return cell.getStatus();
    }
}
