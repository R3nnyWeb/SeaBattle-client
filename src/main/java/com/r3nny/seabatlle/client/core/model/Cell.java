/* (C)2022 */
package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.StarBattle;
import com.r3nny.seabatlle.client.core.controller.GameController;

public class Cell extends Actor {

    public static final float SIZE = 31.37f;
    //   public static final float SIZE = 32f;

    private final int column;
    private final int row;
    private Ship ship;
    private Texture texture;
    private CellStatus status;
    private final ShapeRenderer shape;

    // TODO: Подумать
    private float injuredTime = 0f;
    private float missTime = 0f;
    private float explosionTime = 0f;
    private final Animation injuredAnimation;
    private final Animation burningAnimation;
    private final Animation missAnimation;
    private final Animation explosionAnimation;

    public Cell(float x, float y, int column, int row, Ship ship, CellStatus status) {
        this.column = column;
        this.row = row;
        this.ship = ship;
        super.setX(x);
        super.setY(y);
        this.injuredAnimation = StarBattle.animationManager.getInjuredAnimation();
        this.burningAnimation = StarBattle.animationManager.getBurningAnimation();
        this.missAnimation = StarBattle.animationManager.getMissAnimation();
        this.explosionAnimation = StarBattle.animationManager.getExplosionAnimation();
        this.status = status;
        texture = StarBattle.assetsManager.getInjuredCell();
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
        this.setBounds(x, y, SIZE, SIZE);

        this.addListener(
                new InputListener() {
                    public boolean touchDown(
                            InputEvent event, float x, float y, int pointer, int button) {
                        if (Game.status == GameStatus.PLAYER_TURN) {
                            // TODO: logging
                            GameController.shoot(Cell.this.row, Cell.this.column);
                        }
                        return true;
                    }

                    public void touchUp(
                            InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("up " + Cell.this);
                    }
                });
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());

        shape.begin();
        Gdx.gl20.glLineWidth(3);

        shape.setColor(Color.WHITE);
        shape.rect(getX(), getY(), SIZE, SIZE);
        if (status == CellStatus.NOT_ALLOWED) {
            shape.setColor(Color.RED);
            shape.circle(getX() + SIZE / 2, getY() + SIZE / 2, SIZE / 2 - 8);
        }

        shape.end();

        batch.begin();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (status == CellStatus.INJURED) {
            injuredTime += Gdx.graphics.getDeltaTime();
            if (!injuredAnimation.isAnimationFinished(injuredTime)) {
                TextureRegion currentFrame =
                        (TextureRegion) injuredAnimation.getKeyFrame(injuredTime, false);
                batch.draw(currentFrame, getX(), getY(), Cell.SIZE, Cell.SIZE);
            } else {
                TextureRegion currentFrame =
                        (TextureRegion) burningAnimation.getKeyFrame(injuredTime, true);
                batch.draw(currentFrame, getX(), getY(), Cell.SIZE, Cell.SIZE);
            }
        }
        if (status == CellStatus.KILLED) {
            explosionTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame =
                    (TextureRegion) explosionAnimation.getKeyFrame(explosionTime, false);
            batch.draw(currentFrame, getX(), getY(), Cell.SIZE, Cell.SIZE);
        }

        if (status == CellStatus.MISS) {
            missTime += Gdx.graphics.getDeltaTime();
            batch.draw(
                    (TextureRegion) missAnimation.getKeyFrame(missAnimation.getFrameDuration() * 3),
                    getX(),
                    getY(),
                    Cell.SIZE,
                    Cell.SIZE);
            if (!missAnimation.isAnimationFinished(missTime)) {
                TextureRegion currentFrame =
                        (TextureRegion) missAnimation.getKeyFrame(missTime, false);
                batch.draw(currentFrame, getX(), getY(), Cell.SIZE, Cell.SIZE);
            }
        }

        batch.setColor(color.r, color.g, color.b, 1f);
        //        batch.draw(new Texture("cell.png"),getX(),getY(),Cell.SIZE,Cell.SIZE);

    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        if (status == CellStatus.HEALTHY) {
            shapes.setColor(Color.GREEN);
            shapes.circle(getX() + SIZE / 2, getY() + SIZE / 2, SIZE / 2 - 3);
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Cell{" + "column=" + column + ", row=" + row + ", status=" + status + '}';
    }
}
