/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.actors;

import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import java.util.List;

/**Игровое поле игрока*/
public class PlayerGameField extends GameField {

    private final ShipsCreatingArea shipsCreatingArea;

    public PlayerGameField(float x, float y) {
        super(x, y);
        this.shipsCreatingArea = new ShipsCreatingArea(Game.ENEMY_FIELD_X, Game.FIELD_Y);
        placeShipsOnCreatingArea();
        addShipsToGroup();
    }

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
            shipsByType.get(i).setStartX(startX + (type.getSize() * Cell.SIZE + 20) * i);
            shipsByType.get(i).setStartY(startY - 12);
        }
    }

    private void addShipsToGroup() {
        for (Ship ship : this.ships) {
            super.addActor(ship);
        }
    }

    @Override
    public void createShipsAutomaticaly() {
        super.createShipsAutomaticaly();
        animateShipsCreating();
    }

    private void animateShipsCreating() {
        ShipsCreator.isShipLanding = true;
        for (Ship ship : ships) {
            ship.addAction(
                    StarBattle.animationManager.getShipEnterAction(
                            ship,
                            () -> {
                                ShipsCreator.isShipLanding = false;
                            }));
        }
    }


}
