/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.controller;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.exeptions.CantCreateShipException;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.GameField;
import com.r3nny.seabatlle.client.core.model.Ship;
import com.r3nny.seabatlle.client.core.model.ShipType;

import java.util.LinkedList;
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
                if (!field[y - i][x].isSea()) {
                    return false;
                }
            } else {
                if (cell.getColumn() + i >= GameField.FIELD_SIZE) {
                    return false;
                }
                if (!field[y][x + i].isSea()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void changeCellsStatusAroundShipToMiss(Ship ship, Cell[][] field) {
        int x = ship.getCells().get(0).getColumn();
        int y = ship.getCells().get(0).getRow();
        if (ship.isVertical()) {
            for (int i = 0; i < ship.getType().getSize() + 2; i++) {
                if (y - i + 1 >= 0 && y - i + 1 < field.length) {
                    if (x + 1 < field.length) {
                        field[y - i + 1][x + 1].setMiss();
                    }
                    if (x - 1 >= 0) {
                        field[y - i + 1][x - 1].setMiss();
                    }
                }
            }
            if (y + 1 < field.length) {
                field[y + 1][x].setMiss();
            }
            if (y - ship.getType().getSize() >= 0) {
                field[y - ship.getType().getSize()][x].setMiss();
            }

        } else {
            for (int i = 0; i < ship.getType().getSize() + 2; i++) {

                if (x - 1 + i >= 0 && x - 1 + i < field.length) {
                    if (y + 1 < field.length) {
                        field[y + 1][x - 1 + i].setMiss();
                    }
                    if (y - 1 >= 0) {
                        field[y - 1][x - 1 + i].setMiss();
                    }
                }
            }
            if (x - 1 >= 0) {
                field[y][x - 1].setMiss();
            }
            if (x + ship.getType().getSize() < field.length) {
                field[y][x + ship.getType().getSize()].setMiss();
            }
        }
    }


    public static boolean addShipToGameField(Cell cell, Ship ship, GameField gf) {
        try {
            List<Cell> shipCells = getShipCells(cell, ship, gf.getField());
            linkShipAndCells(ship,shipCells);
            changeCellsStatusAroundShipToMiss(ship, gf.getField());
        } catch (CantCreateShipException e) {
            return false;
        }
        return true;
    }




    private static List<Cell> getShipCells(Cell startCell, Ship ship, Cell[][] field) throws CantCreateShipException {
        List<Cell> cells = new LinkedList<>();
        for (int i = 0; i < ship.getType().getSize(); i++) {
            tryAddCellToList(startCell, ship, field, i, cells);
        }
        return cells;
    }

    private static void tryAddCellToList(Cell startCell, Ship ship, Cell[][] field, int i, List<Cell> cells) throws CantCreateShipException {
        try {
            Cell currentCell = getCurrentCell(startCell, ship, field, i);
            if (currentCell.isSea())
                cells.add(currentCell);
            else
                throw new CantCreateShipException("Cells not clear");
        } catch (IndexOutOfBoundsException e) {
            throw new CantCreateShipException("Ship cant fit on map");
        }
    }

    private static Cell getCurrentCell(Cell startCell, Ship ship, Cell[][] field, int i) throws IndexOutOfBoundsException {
        if (ship.isVertical())
            return field[startCell.getRow() - i][startCell.getColumn()];
        else
            return field[startCell.getRow()][startCell.getColumn() + i];

    }

    private static void linkShipAndCells(Ship ship, List<Cell> shipCells) {
        Cell startCell = shipCells.get(0);
        ship.setPosition(startCell.getX(), startCell.getY());
        ship.setCells(shipCells);
        setShipToCells(ship,shipCells);
    }
    private static void setShipToCells(Ship ship, List<Cell> shipCells) {
        for (Cell cell: ship.getCells()) {
            cell.setShip(ship);
            cell.setHealthy();
        }
    }


    public static void autoCreateShips(GameField gf) {
        Random rd = new Random();
        Cell[][] field = gf.getField();
        List<Ship> ships = gf.getShips();
        int i = 0;
        while (i < shipTypes.length) {
            // TODO: Use thread safe
            int row = rd.nextInt(GameField.FIELD_SIZE);
            int column = rd.nextInt(GameField.FIELD_SIZE);
            Ship ship = ships.get(i);
            if (rd.nextBoolean()) {
                ship.rotate();
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
