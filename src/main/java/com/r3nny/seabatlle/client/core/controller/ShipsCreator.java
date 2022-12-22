/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.controller;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.exeptions.CantCreateShipException;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.actors.GameField;
import com.r3nny.seabatlle.client.core.actors.Ship;
import com.r3nny.seabatlle.client.core.actors.ShipType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**Статические методы для создания кораблей*/
public class ShipsCreator {
    private static Random rd;

    /**Учет успешно созданных кораблей игрока*/
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

    /**Автоматически создает корабли на поле
     * @param gf Игровое поле
     * */
    public static void autoCreateShips(GameField gf) {
        rd = new Random();
        Cell[][] field = gf.getField();
        List<Ship> ships = gf.getShips();
        int createdShips = 0;
        while (createdShips < shipTypes.length) {
            Cell cell = getRandomCellFromField(field);
            Ship ship = ships.get(createdShips);
            if (rd.nextBoolean())
                ship.rotate();
            if (addShipToGameField(cell, ships.get(createdShips), gf))
                createdShips++;

        }
        Gdx.app.log("ShipsCreator", "All ships automatically created");
    }

    private static Cell getRandomCellFromField(Cell[][] field) {
        int row = rd.nextInt(GameField.FIELD_SIZE);
        int column = rd.nextInt(GameField.FIELD_SIZE);
        return field[row][column];
    }

    /**Помещает корабль на поле с началом на определенной клетке
     * @param cell начало корабля
     * @param ship корабль
     * @param gf поле
     * @return успешность добавления*/
    public static boolean addShipToGameField(Cell cell, Ship ship, GameField gf) {
        try {
            List<Cell> shipCells = getShipCells(cell, ship, gf.getField());
            linkShipAndCells(ship, shipCells);
        } catch (CantCreateShipException e) {
            return false;
        }
        setCellsAroundShipMissed(ship, gf.getField());
        return true;
    }

    /**@return Список клеток в которые добавляется корабль
     *@throws CantCreateShipException если нельзя поместить корабль*/
    private static List<Cell> getShipCells(Cell startCell, Ship ship, Cell[][] field)
            throws CantCreateShipException {
        List<Cell> cells = new LinkedList<>();
        for (int i = 0; i < ship.getType().getSize(); i++) {
            tryAddCellToList(startCell, ship, field, i, cells);
        }
        return cells;
    }

    private static void tryAddCellToList(
            Cell startCell, Ship ship, Cell[][] field, int i, List<Cell> cells)
            throws CantCreateShipException {
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

    private static Cell getCurrentCell(Cell startCell, Ship ship, Cell[][] field, int i)
            throws IndexOutOfBoundsException {
        if (ship.isVertical())
            return field[startCell.getRow() - i][startCell.getColumn()];
        else
            return field[startCell.getRow()][startCell.getColumn() + i];
    }

    /**Связывает корабль и клетки в двухстороннем порядке*/
    private static void linkShipAndCells(Ship ship, List<Cell> shipCells) {
        Cell startCell = shipCells.get(0);
        ship.setPosition(startCell.getX(), startCell.getY());
        ship.setCells(shipCells);
        setShipToCells(ship);
    }

    private static void setShipToCells(Ship ship) {
        for (Cell cell : ship.getCells()) {
            cell.setShip(ship);
            cell.setHealthy();
        }
    }

    public static void setCellsAroundShipMissed(Ship ship, Cell[][] field) {
        setMissBeforeAndAfterShip(ship, field);
        setMissOnSidesOfShip(ship, field);
    }

    private static void setMissBeforeAndAfterShip(Ship ship, Cell[][] field) {
        setMissBefore(ship, field);
        setMissAfter(ship, field);
    }

    private static void setMissBefore(Ship ship, Cell[][] field) {
        List<Cell> cells = ship.getCells();
        Cell firstCell = cells.get(0);
        try {
            if (ship.isVertical())
                field[firstCell.getRow() + 1][firstCell.getColumn()].setMiss();
            else
                field[firstCell.getRow()][firstCell.getColumn() - 1].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }

    }

    private static void setMissAfter(Ship ship, Cell[][] field) {
        List<Cell> cells = ship.getCells();
        Cell lastCell = cells.get(cells.size() - 1);
        try {
            if (ship.isVertical())
                field[lastCell.getRow() - 1][lastCell.getColumn()].setMiss();
            else
                field[lastCell.getRow()][lastCell.getColumn() + 1].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }


    }

    private static void setMissOnSidesOfShip(Ship ship, Cell[][] field) {
        Cell startCell = ship.getCells().get(0);
        int x = startCell.getColumn();
        int y = startCell.getRow();
        for (int i = 0; i < ship.getType().getSize() + 2; i++) {
            try {
                if (ship.isVertical()) {
                    if ((y - i + 1) >= 0) {
                        Cell cell = field[y - i + 1][x];
                        setMissLeftFromShip(field, cell);
                        setMissRightFromShip(field, cell);
                    }
                } else {
                    if ((x + i - 1) < GameField.FIELD_SIZE) {
                        Cell cell = field[y][x + i - 1];
                        setMissUnderShip(field, cell);
                        setMissTopOfShip(field, cell);
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }


    private static void setMissLeftFromShip(Cell[][] field, Cell cell) {
        try {
            field[cell.getRow()][cell.getColumn() - 1].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private static void setMissRightFromShip(Cell[][] field, Cell cell) {
        try {
            field[cell.getRow()][cell.getColumn() + 1].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }


    private static void setMissUnderShip(Cell[][] field, Cell cell) {
        try {
            field[cell.getRow() + 1][cell.getColumn()].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private static void setMissTopOfShip(Cell[][] field, Cell cell) {
        try {
            field[cell.getRow() - 1][cell.getColumn()].setMiss();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }


}
