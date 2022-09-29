package com.r3nny.seabattle.client.controller;

import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.model.Cell;

public class CellController {



    public void handleCellClick(Cell cell){
        if (Game.status == GameStatus.SHIPS_STAGE){
            System.out.println(ShipsCreator.createShip(cell));
        }

    }
}
