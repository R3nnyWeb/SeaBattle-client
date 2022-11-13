/* (C)2022 */
package com.r3nny.seabatlle.client.core.controller;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.model.*;
import java.util.List;
import java.util.Random;

public class ShipsCreator {

    public static int createdPlayerShips = 0;

    public static boolean isShipLanding = false;

    public static ShipType[] shipTypes = {
        ShipType.FOUR_DECK,
        ShipType.THREE_DECK,
        ShipType.THREE_DECK,
        ShipType.TWO_DECK,
        ShipType.TWO_DECK,
        ShipType.TWO_DECK,
        ShipType.ONE_DECK,
        ShipType.ONE_DECK,
        ShipType.ONE_DECK,
        ShipType.ONE_DECK
    };

    public static boolean canCreateInCell(Cell cell, Ship ship, Cell[][] field) {
        int x = cell.getColumn();
        int y = cell.getRow();
        for (int i = 0; i < ship.getType().getSize(); i++) {

            if (ship.isVertical()) {
                if (cell.getRow() - i < 0) {
                    return false;
                }
                if (field[y - i][x].getStatus() != CellStatus.SEA) {
                    return false;
                }
            } else {
                if (cell.getColumn() + i >= GameField.FIELD_SIZE) {
                    return false;
                }
                if (field[y][x + i].getStatus() != CellStatus.SEA) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean addShipToGameField(Cell startCell, Ship ship, GameField gf) {
        Gdx.app.log("ShipsCreator", "Trying create ship on " + startCell);
        int x = startCell.getColumn();
        int y = startCell.getRow();
        Cell[][] field = gf.getField();
        Cell[] shipCells = new Cell[ship.getType().getSize()];

        if (!canCreateInCell(startCell, ship, field)) {
            Gdx.app.log("ShipsCreator auto", "Cannot create ship on  Cell{column=" + startCell);
            return false;
        }

        if (ship.isVertical()) {
            for (int i = 0; i < shipCells.length; i++) {
                field[y - i][x].setStatus(CellStatus.HEALTHY);
                shipCells[i] = field[y - i][x];
            }
            for (int i = 0; i < ship.getType().getSize() + 2; i++) {
                // TODO: Rework try try catch
                try {
                    field[y - i + 1][x + 1].setStatus(CellStatus.NOT_ALLOWED);
                } catch (IndexOutOfBoundsException ignored) {

                }

                try {
                    field[y - i + 1][x - 1].setStatus(CellStatus.NOT_ALLOWED);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            try {
                field[y + 1][x].setStatus(CellStatus.NOT_ALLOWED);
            } catch (IndexOutOfBoundsException ignored) {
            }

            try {
                field[y - ship.getType().getSize()][x].setStatus(CellStatus.NOT_ALLOWED);
            } catch (IndexOutOfBoundsException ignored) {
            }
        } else {
            for (int i = 0; i < shipCells.length; i++) {
                field[y][x + i].setStatus(CellStatus.HEALTHY);
                shipCells[i] = field[y][x + i];
            }

            for (int i = 0; i < ship.getType().getSize() + 2; i++) {
                // TODO: Rework try try catch
                try {
                    field[y + 1][x - 1 + i].setStatus(CellStatus.NOT_ALLOWED);
                } catch (IndexOutOfBoundsException ignored) {
                }

                try {
                    field[y - 1][x - 1 + i].setStatus(CellStatus.NOT_ALLOWED);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            try {
                field[y][x - 1].setStatus(CellStatus.NOT_ALLOWED);
            } catch (IndexOutOfBoundsException ignored) {
            }

            try {
                field[y][x + ship.getType().getSize()].setStatus(CellStatus.NOT_ALLOWED);
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        ship.setCells(shipCells);
        ship.setX(shipCells[0].getX());
        ship.setY(shipCells[0].getY());
        for (Cell shipCell : shipCells) {
            shipCell.setShip(ship);
        }
        Gdx.app.log("ShipsCreator", "Ship created " + ship);

        return true;
    }

    public static void autoCreateShips(GameField gf) {
        Random rd = new Random();
        Cell[][] field = gf.getField();
        List<Ship> ships = gf.getShips();
        int i = 0;
        while (i < shipTypes.length) {
            // TODO: Use thread safe
            int row = rd.nextInt(10);
            int column = rd.nextInt(10);
            Ship ship = ships.get(i);
            // TODO: Разобратся с рандомом. Без sleep не работает
            if (rd.nextBoolean()) {
                ship.setVertical(!ship.isVertical());
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (addShipToGameField(field[row][column], ships.get(i), gf)) {
                i++;
            }
        }
        Gdx.app.log("ShipsCreator", "All ships automatically created");
    }
}
