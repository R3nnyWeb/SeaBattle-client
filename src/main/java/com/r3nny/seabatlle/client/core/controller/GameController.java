package com.r3nny.seabatlle.client.core.controller;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.CellStatus;

import static com.r3nny.seabatlle.client.core.Game.playerField;


public class GameController {

    public static CellStatus shoot(int row, int column){
        Gdx.app.log(Game.status.toString(), row + " " + column + "");

        //TODO : REWORK

        Cell cell;
        if(Game.status == GameStatus.PLAYER_TURN){
            cell = Game.enemy.getField()[row][column];
        }else{
            cell = playerField.getField()[row][column];
        }
        if(cell.getStatus() == CellStatus.SEA){
            if(Game.status == GameStatus.PLAYER_TURN){
                Game.status = GameStatus.ENEMY_TURN;
            } else{
                Game.status = GameStatus.PLAYER_TURN;
            }
            cell.setStatus(CellStatus.MISS);
            return CellStatus.MISS;

        }
        if(cell.getStatus() == CellStatus.HEALTHY){
            //TODO: Проверка на убийство
            cell.setStatus(CellStatus.INJURED);
            return CellStatus.INJURED;
        }


        return cell.getStatus();
    }
}
