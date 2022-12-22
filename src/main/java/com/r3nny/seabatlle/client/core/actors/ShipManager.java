/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.actors;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.HashSet;
import java.util.Set;

/**Учет количества кораблей и отображение количества*/
public class ShipManager extends Group {

    private final Set<ShipsCountDTO> shipCounts;

    public ShipManager(float x, float y) {
        shipCounts = new HashSet<>();
        shipCounts.add(new ShipsCountDTO(x, y, ShipType.FOUR_DECK, 1));
        shipCounts.add(
                new ShipsCountDTO(
                        x + ShipType.FOUR_DECK.getSize() * Cell.SIZE + 10,
                        y,
                        ShipType.THREE_DECK,
                        2));
        shipCounts.add(new ShipsCountDTO(x, y - Cell.SIZE, ShipType.TWO_DECK, 3));
        shipCounts.add(
                new ShipsCountDTO(
                        x + ShipType.FOUR_DECK.getSize() * Cell.SIZE + 10,
                        y - Cell.SIZE,
                        ShipType.ONE_DECK,
                        4));

        for (ShipsCountDTO shipType : shipCounts) {
            super.addActor(shipType);
        }
    }

    public void decByType(ShipType type) {
        shipCounts.stream().filter(s -> s.getType() == type).findFirst().ifPresent(ShipsCountDTO::dec);

    }

    public boolean isAllShipsKilled(){
        return shipCounts.stream().filter(c-> c.getCount()!=0).toList().isEmpty();
    }
}
