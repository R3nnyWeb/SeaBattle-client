/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.actors;

import static com.r3nny.seabatlle.client.core.controller.ShipsCreator.isAnyShipLanding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabatlle.client.core.game.Game;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.ShipsCreator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**Корабль*/
public class Ship extends Actor {
    private final ShipType type;
    private List<Cell> cells;

    /**Координата для окна создания корабля*/
    private float startX;
    /**Координата для окна создания корабля*/
    private float startY;
    private boolean isVertical;
    private boolean isSelected;
    private final ShapeRenderer shape;
    private final Sprite sprite;

    /**Обработчик нажатия на корабль*/
    private class ShipInputListener extends InputListener {

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            unSelect();
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (canMoveShip()) {
                select();
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
                        ShipsCreator.createdPlayerShips++;
                        Game.player.setShipsReady(
                                ShipsCreator.createdPlayerShips
                                        == ShipsCreator.shipTypes.length);
                    } else {
                        resetCoordinates();
                    }
                } else {
                    resetCoordinates();
                }
            }
        }

        private void animateShip() {
            isAnyShipLanding = true;
            Ship.this.addAction(
                    StarBattle.animationManager.getShipEnterAction(
                            Ship.this,
                            () -> isAnyShipLanding = false));
            StarBattle.soundManager.playShipEnterSound();
            updateBounds();
        }



        /**@return Клетка на которую был брошен корабль*/
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
        return isShipNotPlaced() && !isAnyShipLanding;
    }
    private boolean isShipNotPlaced() {
        return this.cells.isEmpty();
    }

    private void updatePosition(float v, float v1) {
        setPosition(v, v1);
        updateBounds();
    }

    private void resetCoordinates() {
        setPosition(getStartX(), getStartY());
    }



    private void select() {
        Ship.this.isSelected = true;
        StarBattle.soundManager.playFocusButton();
    }

    private void unSelect() {
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

    public void makeCellsKilled() {
        for (Cell c : cells) {
            c.setKilled();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isSelected)
            drawBounds(batch);
        drawShip(batch, parentAlpha);

    }



    private void drawBounds(Batch batch) {
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        if (isVertical)
            shape.rect(getX(), getY(), Cell.SIZE, type.getSize() * Cell.SIZE);
        else
            shape.rect(getX(), getY(), type.getSize() * Cell.SIZE, Cell.SIZE);
        shape.end();
        batch.begin();
    }

    private void drawShip(Batch batch, float parentAlpha) {
        Color color = getColor();
        sprite.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
        sprite.setColor(color.r, color.g, color.b, 1f);
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


    /**Поворачивает корабль на 90 градусов*/
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

}
