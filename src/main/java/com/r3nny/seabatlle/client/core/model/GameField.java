/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import com.r3nny.seabatlle.client.core.utils.ShipManager;

import java.util.LinkedList;
import java.util.List;


public abstract class GameField extends Group {
    public static final int FIELD_SIZE = 10;
    private final float x;
    private final float y;
    private ShipManager shipManager;
    private boolean isShipsReady;
    private final Cell[][] field;
    protected final List<Ship> ships;


    public GameField(float x, float y) {
        this.x = x;
        this.y = y;
        field = initCells();
        ships = initShips();
    }


    private Cell[][] initCells() {
        Cell[][] field = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(x + Cell.SIZE * j, y - Cell.SIZE * i, j, i);
                super.addActor(field[i][j]);
            }
        }
        return field;
    }

    private List<Ship> initShips() {
        return createShipsAllTypes(ShipsCreator.shipTypes);
    }

    private List<Ship> createShipsAllTypes(ShipType[] shipTypes) {
        List<Ship> ships = new LinkedList<>();
        for (int i = 0; i < shipTypes.length; i++) {
            Ship ship = new Ship(0, 0, ShipsCreator.shipTypes[i]);
            ships.add(ship);
        }
        return ships;
    }



    public void createShipsAutomaticaly() {
        StarBattle.soundManager.playShipEnterSound();
        clearField();
        ShipsCreator.autoCreateShips(this);
        clearAllMissed();
    }



    private void clearField() {
        for (Cell[] cells : this.field) {
            for (int j = 0; j < field.length; j++) {
                cells[j].setSea();
            }
        }
    }




    public void killShip(Ship ship) {
        ship.makeCellsKilled();
        decByShipType(ship.getType());
        ShipsCreator.changeCellsStatusAroundShip(ship, this.getField());
        removeActor(ship);
    }



    public void clearAllMissed() {
        for (Cell[] cells : field) {
            for (int j = 0; j < field.length; j++) {
                if (cells[j].isMissed()) {
                    cells[j].setSea();
                }
            }
        }
    }
    public void createShipsManager() {
        shipManager = new ShipManager(x, y - (Cell.SIZE * field.length) - 40);
        super.addActor(shipManager);
    }

    private void decByShipType(ShipType type) {
        shipManager.decByType(type);
    }

    public boolean isShipsReady() {
        return isShipsReady;
    }

    public void setShipsReady(boolean shipsReady) {
        isShipsReady = shipsReady;
    }

    public Cell[][] getField() {
        return field;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
