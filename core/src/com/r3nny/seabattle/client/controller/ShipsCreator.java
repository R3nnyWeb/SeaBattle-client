package com.r3nny.seabattle.client.controller;

import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.model.Ship;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.CellStatus;
import com.r3nny.seabattle.client.model.GameField;
import com.r3nny.seabattle.client.model.ShipType;


public class ShipsCreator {
    public static ShipType[] shipTypes = {ShipType.FOUR_DECK,
            ShipType.THREE_DECK, ShipType.THREE_DECK,
            ShipType.TWO_DECK, ShipType.TWO_DECK, ShipType.TWO_DECK,
            ShipType.ONE_DECK, ShipType.ONE_DECK, ShipType.ONE_DECK, ShipType.ONE_DECK};
    public static int currentShipType = 0;

    public static boolean canCreateShipHere(Cell cell, Cell[][] field){
        int x = cell.getFieldX();
        int y = cell.getFieldY();
        for (int i = 0; i < shipTypes[currentShipType].getSize(); i++) {
            if (cell.getFieldX() + i >= GameField.FIELD_SIZE) {
                return false;
            }
            if (field[y][x + i].getStatus() != CellStatus.SEA) {
                return false;
            }
        }
        return  true;
    }
    public static boolean createShip(Cell cell) {
        System.out.println("I must create sheep on:  " + cell);
        int x = cell.getFieldX();
        int y = cell.getFieldY();
        Cell[][] field = Game.playerField.getField();
       if (!canCreateShipHere(cell,field)){
           return false;
       }
        Cell[] shipCells = new Cell[shipTypes[currentShipType].getSize()];
        for (int i = 0; i < shipCells.length; i++) {
            field[y][x + i].setStatus(CellStatus.HEALTHY);
            shipCells[i] = field[y][x + i];
        }
        Ship ship = new Ship(shipCells, shipTypes[currentShipType]);
        for (int i = 0; i < shipCells.length; i++) {
            shipCells[i].setShip(ship);
        }



        Game.playerView.addShipView(ship);
        currentShipType++;
        if (currentShipType ==  shipTypes.length) {
            Game.status = GameStatus.PLAYER_TURN;
        }
        return true;
    }


}
