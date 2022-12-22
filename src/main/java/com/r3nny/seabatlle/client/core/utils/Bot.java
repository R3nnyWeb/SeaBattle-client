package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.game.Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Bot {

    private float timeFromLastShoot = 0F;

    private final float TIME_BETWEEN_SHOOTS = 1F;
    private final Set<Cell> cellsToShoot;

    private boolean isNeedToKill = false;

    private Stack<Cell> possibleCellsToShoot;


    public Bot() {
        this.cellsToShoot = initCellsToShoot();

    }

    private Set<Cell> initCellsToShoot() {
        Set<Cell> cellsToShoot = new HashSet<Cell>();
        Cell[][] fields = Game.player.getField();
        for (Cell[] cell : fields) {
            cellsToShoot.addAll(Arrays.asList(cell));
        }
        return cellsToShoot;
    }

    public void update() {
        timeFromLastShoot += Gdx.graphics.getDeltaTime();
    }
}
