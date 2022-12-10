/* (C)2022 */
package com.r3nny.seabatlle.client.core.model;

import static com.r3nny.seabatlle.client.core.controller.ShipsCreator.isShipLanding;
import static com.r3nny.seabatlle.client.core.controller.ShipsCreator.shipTypes;
import static com.r3nny.seabatlle.client.core.model.CellStatus.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import com.r3nny.seabatlle.client.core.utils.ShipManager;
import java.util.LinkedList;
import java.util.List;

public class GameField extends Group {
    public static final int FIELD_SIZE = 10;
    private final float x;
    private final float y;

    private ShipManager shipManager;
    private final boolean isPlayer;
    private boolean isShipsReady;
    private final Cell[][] field;
    private final List<Ship> ships;
    private ShipsCreatingArea area;

    public GameField(float x, float y, boolean isPlayer) {
        this.isPlayer = isPlayer;
        this.x = x;
        this.y = y;

        this.area = new ShipsCreatingArea(Game.ENEMY_FIELD_X, Game.FIELD_Y);

        field = initCells();
        ships = initShips();

        if (!isPlayer) {
            Gdx.app.log("GameField", " Autocreating enemy ships ");
            initAutoShips();
        } else {
            initStartCoords(area);
        }
    }

    private void initStartCoords(ShipsCreatingArea area) {
        float x = area.controlHelp.getX();

        Ship fourDeskShip =
                ships.stream().filter(s -> s.getType() == ShipType.FOUR_DECK).findFirst().get();
        fourDeskShip.setStartX(x);
        fourDeskShip.setStartY(area.fourDeskLabel.getY() - 12);
        List<Ship> treeDeskShips =
                ships.stream().filter(s -> s.getType() == ShipType.THREE_DECK).toList();
        for (int i = 0; i < treeDeskShips.size(); i++) {
            treeDeskShips
                    .get(i)
                    .setStartX(x + (ShipType.THREE_DECK.getSize() * Cell.SIZE + 20) * i);
            treeDeskShips.get(i).setStartY(area.treeDeskLabel.getY() - 12);
        }
        List<Ship> twoDeskShips =
                ships.stream().filter(s -> s.getType() == ShipType.TWO_DECK).toList();
        for (int i = 0; i < twoDeskShips.size(); i++) {
            twoDeskShips.get(i).setStartX(x + (ShipType.TWO_DECK.getSize() * Cell.SIZE + 20) * i);
            twoDeskShips.get(i).setStartY(area.twoDeskLabel.getY() - 12);
        }
        List<Ship> oneDeskShips =
                ships.stream().filter(s -> s.getType() == ShipType.ONE_DECK).toList();
        for (int i = 0; i < oneDeskShips.size(); i++) {
            oneDeskShips.get(i).setStartX(x + (ShipType.ONE_DECK.getSize() * Cell.SIZE + 20) * i);
            oneDeskShips.get(i).setStartY(area.oneDeskLabel.getY() - 12);
        }
    }

    private List<Ship> initShips() {
        List<Ship> ships = new LinkedList<>();
        for (int i = 0; i < shipTypes.length; i++) {
            // TODO: заменить null на пустой лист
            Ship ship = new Ship(0, 0, null, ShipsCreator.shipTypes[i]);
            ships.add(ship);
        }

        for (Ship ship : ships) {
            super.addActor(ship);
        }
        return ships;
    }

    private Cell[][] initCells() {
        Cell[][] field = new Cell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(x + Cell.SIZE * j, y - Cell.SIZE * i, j, i, null, SEA);
                super.addActor(field[i][j]);
            }
        }
        return field;
    }

    public void initAutoShips() {

        for (Ship ship : ships) {
            super.removeActor(ship);
        }
        for (Cell[] cells : field) {
            for (int j = 0; j < field.length; j++) {
                cells[j].setStatus(SEA);
            }
        }
        ShipsCreator.autoCreateShips(this);
        if (isPlayer) {
            isShipLanding = true;
            for (Ship ship : ships) {
                ship.addAction(
                        StarBattle.animationManager.getShipEnterAction(
                                ship,
                                () -> {
                                    isShipLanding = false;
                                }));
                super.addActor(ship);
            }
        }
        StarBattle.soundManager.playShipEnterSound();
        clearAllNotAllowed();
        if (!isPlayer) {
            this.isShipsReady = true;
        }
    }

    public void removeArea() {
        super.removeActor(area);
    }

    public void clearAllNotAllowed() {
        for (Cell[] cells : field) {
            for (int j = 0; j < field.length; j++) {
                if (cells[j].getStatus() == NOT_ALLOWED || cells[j].getStatus() == MISS) {
                    cells[j].setStatus(SEA);
                }
            }
        }
    }

    public void createShipsManager() {
        shipManager = new ShipManager(x, y - (Cell.SIZE * field.length) - 40);
        super.addActor(shipManager);
    }

    public void decByShipType(ShipType type) {
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
