package com.r3nny.seabatlle.client.core.controller;

import com.badlogic.gdx.Gdx;
import com.r3nny.seabatlle.client.core.actors.Cell;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.game.GameStatus;

import java.util.*;

public class Bot {
    private boolean isDirectionChanged;
    enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }
    private final Random rd = new Random();
    private float timeFromLastShoot = 0F;
    private List<Cell> hittedCells;
    private Optional<Direction> direction = Optional.empty();
    private final List<Cell> cellsToShoot;


    public Bot() {
        this.hittedCells = new LinkedList<>();
        this.cellsToShoot = initCellsToShoot();

    }

    private List<Cell> initCellsToShoot() {
        List<Cell> cellsToShoot = new ArrayList<>();
        Cell[][] fields = Game.player.getField();
        for (Cell[] cell : fields) {
            cellsToShoot.addAll(Arrays.asList(cell));
        }
        return cellsToShoot;
    }

    public void update() {
        if (isEnemyTurn()) {
            timeFromLastShoot += Gdx.graphics.getDeltaTime();
        }
        if (isItTimeToShoot()) {
            Gdx.app.log("Bot", "Time to shoot======================");
            if (direction.isPresent())
                keepDestroyingShip();
             else
                doRandomShot();
            timeFromLastShoot = 0;
        }

    }

    private boolean isEnemyTurn() {
        return GameStatus.ENEMY_TURN == Game.status;
    }

    private boolean isItTimeToShoot() {
        float TIME_BETWEEN_SHOOTS = 1F;
        return timeFromLastShoot > TIME_BETWEEN_SHOOTS && isEnemyTurn();
    }
    private void keepDestroyingShip() {
        Gdx.app.log("Bot", "Have direction: " + direction.get() + " hitted: " + hittedCells);
        var optCell = getNextCellInCurrentDirection(direction.get());
        if (optCell.isPresent()) {
            shootOnSameDirection(optCell.get());
        } else {
            Gdx.app.log("Bot", "Cant shoot");
            changeDirection(direction.get());
        }
    }

    private Optional<Cell> getNextCellInCurrentDirection(Direction direction) {
        Cell[][] field = Game.player.getField();
        Cell cell = getLastHittedCell();
        int row = cell.getRow();
        int column = cell.getColumn();
        try {
            Cell nextCell;
            switch (direction) {
                case TOP -> nextCell = field[row - 1][column];
                case BOTTOM -> nextCell = field[row + 1][column];
                case LEFT -> nextCell = field[row][column - 1];
                default -> nextCell = field[row][column + 1];
            }
            return nextCell.isSea() || nextCell.isHealthy() ? Optional.of(nextCell) : Optional.empty();
        } catch (IndexOutOfBoundsException ignored) {
            return Optional.empty();
        }
    }

    private Cell getLastHittedCell() {
        return hittedCells.get(hittedCells.size() - 1);
    }

    private void shootOnSameDirection(Cell cell) {
        Gdx.app.log("Bot", "Can shoot on this");
        ShootController.shoot(cell.getRow(), cell.getColumn());
        cellsToShoot.remove(cell);
        if (cell.isMissed()) {
            Gdx.app.log("Bot", "Missed");
            changeDirection(direction.get());
        } else {
            if (cell.isInjured()){
                Gdx.app.log("Bot", "Injured. Adding cell to hitted");
                hittedCells.add(cell);
            }
            else{
                Gdx.app.log("Bot", "Killed");
                resetBot();
            }
        }
    }

    private void changeDirection(Direction current) {
        removeAllExtraCells();
        Gdx.app.log("Bot", "Changing direction. isChanged: " + isDirectionChanged);
        if (isDirectionChanged) {
            changeDirectionToCorner(current);
            Gdx.app.log("Bot", "Changed corner to " + direction.get());
        } else {
            changeDirectionToOpposite(current);
            Gdx.app.log("Bot", "Changed oposite to " + direction.get());
        }
        isDirectionChanged = !isDirectionChanged;
    }
    private void removeAllExtraCells() {
        Cell firstCell = hittedCells.get(0);
        hittedCells = new ArrayList<>();
        hittedCells.add(firstCell);
    }


    private void changeDirectionToOpposite(Direction current) {
        switch (current) {
            case TOP -> direction = Optional.of(Direction.BOTTOM);
            case BOTTOM -> direction = Optional.of(Direction.TOP);
            case LEFT -> direction = Optional.of(Direction.RIGHT);
            default -> direction = Optional.of(Direction.LEFT);
        }
    }

    private void changeDirectionToCorner(Direction current) {
        switch (current) {
            case TOP -> direction = Optional.of(Direction.RIGHT);
            case BOTTOM -> direction = Optional.of(Direction.LEFT);
            case LEFT -> direction = Optional.of(Direction.TOP);
            default -> direction = Optional.of(Direction.BOTTOM);
        }
    }
    private void resetBot() {
        Gdx.app.log("Bot", "ResetBot");
        direction = Optional.empty();
        hittedCells = new ArrayList<>();
        isDirectionChanged = false;
    }
    private void doRandomShot() {
        Cell cell = getRandomCellToShoot();
        Gdx.app.log("Bot", "Random shoot to cell " + cell);
        ShootController.shoot(cell.getRow(), cell.getColumn());
        if (cell.isInjured()) {
            Gdx.app.log("Bot", "Injured");
            hittedCells.add(cell);
            cellsToShoot.remove(cell);
            setRandomDirection();
        } else {
            Gdx.app.log("Bot", "Miss or killed");
            resetBot();
        }
    }
    private Cell getRandomCellToShoot() {
        Cell cell = getRandomCell();
        while (!(cell.isSea() || cell.isHealthy())) {
            cell = getRandomCell();
        }
        return cell;
    }

    private Cell getRandomCell() {
        return cellsToShoot.get(rd.nextInt(cellsToShoot.size()));
    }
    private void setRandomDirection() {
        Direction[] directions = Direction.values();
        direction = Optional.of(directions[rd.nextInt(directions.length)]);
    }






}



