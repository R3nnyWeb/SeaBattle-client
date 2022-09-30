package com.r3nny.seabattle.client.controller;

import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.model.*;


public class ShipsCreator {
    public static ShipType[] shipTypes = {ShipType.FOUR_DECK,
            ShipType.THREE_DECK, ShipType.THREE_DECK,
            ShipType.TWO_DECK, ShipType.TWO_DECK, ShipType.TWO_DECK,
            ShipType.ONE_DECK, ShipType.ONE_DECK, ShipType.ONE_DECK, ShipType.ONE_DECK};
    public static int currentShipType = 0;

    public static boolean canCreateShipHere(Cell cell, Cell[][] field) {
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
        return true;
    }

    public static boolean createShip(Cell cell) {
        System.out.println("I must create sheep on:  " + cell);
        int x = cell.getFieldX();
        int y = cell.getFieldY();
        Cell[][] field = Game.playerField.getField();
        if (!canCreateShipHere(cell, field)) {
            return false;
        }
        Cell[] shipCells = new Cell[shipTypes[currentShipType].getSize()];
        for (int i = 0; i < shipCells.length; i++) {
            field[y][x + i].setStatus(CellStatus.HEALTHY);
            shipCells[i] = field[y][x + i];
        }

        for (int i = 0; i < shipTypes[currentShipType].getSize() + 2; i++) {
            //TODO: Rework try try catch
            try {
                field[y + 1][x - 1 + i].setStatus(CellStatus.NOT_ALLOWED);
            }catch (IndexOutOfBoundsException ignored) {
            }
            ;
            try {
                field[y - 1][x - 1 + i].setStatus(CellStatus.NOT_ALLOWED);
            } catch (IndexOutOfBoundsException ignored) {
            }
            ;
        }
        try {
            field[y][x - 1].setStatus(CellStatus.NOT_ALLOWED);
        } catch (IndexOutOfBoundsException ignored) {
        }
        ;
        try {
            field[y][x + shipTypes[currentShipType].getSize()].setStatus(CellStatus.NOT_ALLOWED);
        } catch (IndexOutOfBoundsException ignored) {
        }
        ;

        Ship ship = new Ship(shipCells, shipTypes[currentShipType]);
        for (Cell shipCell : shipCells) {
            shipCell.setShip(ship);
        }


        Game.playerView.addShipView(ship);
        currentShipType++;
        if (currentShipType == shipTypes.length) {
            Game.status = GameStatus.PLAYER_TURN;
        }
        return true;
    }


}
