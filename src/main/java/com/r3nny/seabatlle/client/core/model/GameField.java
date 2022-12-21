/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import com.r3nny.seabatlle.client.core.utils.ShipManager;

import java.util.LinkedList;
import java.util.List;


public  class GameField extends Group {
    public static final int FIELD_SIZE = 10;
    private final float x;
    private final float y;
    private ShipManager shipManager;
    private final boolean isPlayer;
    private boolean isShipsReady;
    private final Cell[][] field;
    private final List<Ship> ships;
    private final ShipsCreatingArea shipsCreatingArea;

    public GameField(float x, float y, boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.x = x;
        this.y = y;
        this.shipsCreatingArea = new ShipsCreatingArea(Game.ENEMY_FIELD_X, Game.FIELD_Y);
        field = initCells();
        ships = initShips();
        if (!isPlayer)
            initAutoShips();
        else
            placeShipsOnCreatingArea();
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
        List<Ship> ships = createShipsAllTypes(ShipsCreator.shipTypes);
        addShipsToGroup(ships);
        return ships;
    }

    private List<Ship> createShipsAllTypes(ShipType[] shipTypes) {
        List<Ship> ships = new LinkedList<>();
        for (int i = 0; i < shipTypes.length; i++) {
            Ship ship = new Ship(0, 0, ShipsCreator.shipTypes[i]);
            ships.add(ship);
        }
        return ships;
    }

    private void addShipsToGroup( List<Ship> ships) {
        for (Ship ship : ships) {
            super.addActor(ship);
        }
    }


    public void initAutoShips() {
        StarBattle.soundManager.playShipEnterSound();
        removeShipsFromGroup();
        clearField();
        ShipsCreator.autoCreateShips(this);
        if (this.isPlayer) {
            animateShipsCreatingAndAddToGroup();
        }
        clearAllMissed();
        if (!this.isPlayer) {
            this.isShipsReady = true;
        }
    }


    private void removeShipsFromGroup() {
        for (Ship ship : this.ships) {
            super.addActor(ship);
        }
    }

    private void clearField() {
        for (Cell[] cells : this.field) {
            for (int j = 0; j < field.length; j++) {
                cells[j].setSea();
            }
        }
    }

    private void animateShipsCreatingAndAddToGroup() {
        ShipsCreator.isShipLanding = true;
        for (Ship ship : ships) {
            ship.addAction(
                    StarBattle.animationManager.getShipEnterAction(ship, () -> {
                        ShipsCreator.isShipLanding = false;
                    }));
            super.addActor(ship);
        }
    }


    // TODO: Подумать
    private void placeShipsOnCreatingArea() {
        placeByShipType(ShipType.FOUR_DECK, shipsCreatingArea.fourDeskLabel.getY());
        placeByShipType(ShipType.THREE_DECK, shipsCreatingArea.treeDeskLabel.getY());
        placeByShipType(ShipType.TWO_DECK, shipsCreatingArea.twoDeskLabel.getY());
        placeByShipType(ShipType.ONE_DECK, shipsCreatingArea.oneDeskLabel.getY());
    }

    private void placeByShipType(ShipType type, float startY) {
        float startX = shipsCreatingArea.controlsHelp.getX();
        List<Ship> shipsByType = ships.stream().filter(s -> s.getType() == type).toList();
        for (int i = 0; i < shipsByType.size(); i++) {
            shipsByType
                    .get(i)
                    .setStartX(startX + (type.getSize() * Cell.SIZE + 20) * i);
            shipsByType.get(i).setStartY(startY - 12);
        }
    }


    public void killShip(Ship ship) {
        ship.makeCellsKilled();
        decByShipType(ship.getType());
        ShipsCreator.changeCellsStatusAroundShip(ship, this.getField());
        removeActor(ship);
    }


    public void removeArea() {
        super.removeActor(shipsCreatingArea);

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
