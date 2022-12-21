/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.model;

import static com.r3nny.seabatlle.client.core.controller.ShipsCreator.isShipLanding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Ship extends Actor {
    private final ShipType type;

    // TODO : Переделать на коллекцию
    private List<Cell> cells;
    private float startX;
    private float startY;
    private boolean isVertical;
    private boolean isSelected;
    private boolean isKilled;
    private final ShapeRenderer shape;
    private final Sprite sprite;

    private class ShipInputListener extends InputListener {

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            unSelectShip();
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (canMoveShip()) {
                selectShip();
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (canMoveShip()) {
                if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                    rotate();
                    StarBattle.soundManager.playClickSound();
                }
            }
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (canMoveShip()) {
                    updatePosition(event.getStageX() - 10, event.getStageY() - 10);
                }
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (canMoveShip()) {
                Optional<Cell> optionalCell = getCellFromEvent(event);
                if (optionalCell.isPresent()) {
                    Cell cell = optionalCell.get();
                    if (ShipsCreator.addShipToGameField(cell, Ship.this, Game.player)) {
                        updatePosition(cell.getX(), cell.getY());
                        animateShip();
                    } else {
                        resetCoordinates();
                    }
                } else {
                    resetCoordinates();
                }
            }
        }

        private void animateShip() {
            isShipLanding = true;
            Ship.this.addAction(
                    StarBattle.animationManager.getShipEnterAction(
                            Ship.this,
                            () -> {
                                isShipLanding = false;
                                ShipsCreator.createdPlayerShips++;
                                Game.player.setShipsReady(
                                        ShipsCreator.createdPlayerShips
                                                == ShipsCreator.shipTypes.length);
                            }));
            StarBattle.soundManager.playShipEnterSound();
            updateBounds();
        }

        private Optional<Cell> getCellFromEvent(InputEvent event) {
            float x = event.getStageX() - 5;
            float y = event.getStageY() - 5;
            Cell[][] field = Game.player.getField();
            for (Cell[] cells : field) {
                for (int j = 0; j < field.length; j++) {
                    Cell currentCell = cells[j];
                    float startX = cells[j].getX();
                    float startY = cells[j].getY();
                    float endX = startX + cells[j].getWidth();
                    float endY = startY + cells[j].getHeight();
                    // TODO: Contains написать
                    if ((x > startX) && (x < endX) && (y > startY) && (y < endY)) {
                        return Optional.of(currentCell);
                    }
                }
            }
            return Optional.empty();
        }
    }

    public Ship(float x, float y, ShipType type) {
        shape = new ShapeRenderer();
        this.cells = Collections.emptyList();
        this.type = type;
        super.setX(x);
        super.setY(y);
        this.sprite = initSprite();
        updateBounds();
        this.addListener(new ShipInputListener());
    }

    public boolean canMoveShip() {
        return isShipNotPlaced() && !isShipLanding;
    }

    private void updatePosition(float v, float v1) {
        setPosition(v, v1);
        updateBounds();
    }

    private void resetCoordinates() {
        setPosition(getStartX(), getStartY());
    }

    private boolean isShipNotPlaced() {
        return this.cells.isEmpty();
    }

    private void selectShip() {
        Ship.this.isSelected = true;
        StarBattle.soundManager.playFocusButton();
    }

    private void unSelectShip() {
        Ship.this.isSelected = false;
    }

    private Sprite initSprite() {
        Sprite texture;
        switch (type) {
            case ONE_DECK -> texture = new Sprite(StarBattle.assetsManager.getOneDeckShip());
            case TWO_DECK -> texture = new Sprite(StarBattle.assetsManager.getTwoDeckShip());
            case THREE_DECK -> texture = new Sprite(StarBattle.assetsManager.getThreeDeckShip());
            default -> texture = new Sprite(StarBattle.assetsManager.getFourDeckShip());
        }
        texture.setSize(Cell.SIZE * type.getSize(), Cell.SIZE);
        return texture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void updateBounds() {
        if (isVertical) {
            this.setBounds(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        } else {
            this.setBounds(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void makeCellsKilled() {
        this.isKilled = true;
        for (Cell c : cells) {
            c.setKilled();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isSelected) drawBounds(batch);

        Color color = getColor();
        sprite.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);

        sprite.setColor(color.r, color.g, color.b, 1f);
    }

    private void drawBounds(Batch batch) {
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        if (isVertical) shape.rect(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        else shape.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);

        shape.end();
        batch.begin();
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.setColor(Color.RED);
        if (isVertical) {
            shapes.rect(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);

        } else {
            shapes.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        }
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        setX(startX);
        this.startX = startX;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void rotate() {
        boolean rotation = !isVertical();
        sprite.rotate90(rotation);
        isVertical = rotation;
        updateBounds();
        sprite.setSize(super.getWidth(), super.getHeight());
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        setY(startY);
        this.startY = startY;
    }

    public ShipType getType() {
        return type;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Ship{"
                + "x="
                + getX()
                + "type="
                + type
                + ", isVertical="
                + isVertical
                + ", isSelected="
                + isSelected
                + '}';
    }
}
