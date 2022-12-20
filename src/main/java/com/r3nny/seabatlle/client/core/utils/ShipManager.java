/* (C)2022 */
package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.r3nny.seabatlle.client.core.model.Cell;
import com.r3nny.seabatlle.client.core.model.ShipType;
import com.r3nny.seabatlle.client.core.model.ShipsCountDTO;
import java.util.ArrayList;
import java.util.List;

public class ShipManager extends Group {

    private final List<ShipsCountDTO> shipCounts;

    public ShipManager(float x, float y) {

        shipCounts = new ArrayList<>();
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

    public void decByType(ShipType shipType) {
        ShipsCountDTO shipCount =
                shipCounts.stream().filter(s -> s.getType() == shipType).findFirst().get();
        shipCount.dec();
    }



}
