/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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


enum CellStatus {
    SEA,
    MISS,
    HEALTHY,
    INJURED,
    KILLED
}


public class Cell extends Actor {

    public static final float SIZE = 31.37f;

    private final int column;
    private final int row;
    private Ship ship;
    private CellStatus status;
    private final ShapeRenderer shape;
    private float animationTime = 0f;

    private final Animation injuredAnimation;
    private final Animation burningAnimation;
    private final Animation missAnimation;
    private final Animation explosionAnimation;

    public Cell(float x, float y, int column, int row) {
        this.column = column;
        this.row = row;
        this.ship = null;
        super.setX(x);
        super.setY(y);
        this.injuredAnimation = StarBattle.animationManager.getInjuredAnimation();
        this.burningAnimation = StarBattle.animationManager.getBurningAnimation();
        this.missAnimation = StarBattle.animationManager.getMissAnimation();
        this.explosionAnimation = StarBattle.animationManager.getExplosionAnimation();
        this.status = CellStatus.SEA;
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
        this.setBounds(x, y, SIZE, SIZE);

        this.addListener(
                new InputListener() {
                    public boolean touchDown(
                            InputEvent event, float x, float y, int pointer, int button) {
                        if (Game.status == GameStatus.PLAYER_TURN) {
                            GameController.shoot(Cell.this.row, Cell.this.column);
                        }
                        return true;
                    }
                });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        animationTime += Gdx.graphics.getDeltaTime();
        drawBorders(batch);
        setUpBatch(batch, parentAlpha);
        drawInjured(batch);
        drawKilled(batch);
        drawMissed(batch);
        tearDownBatch(batch);
    }


    private void drawBorders(Batch batch) {
        setUpShapeRenderer(batch);
        shape.rect(getX(), getY(), SIZE, SIZE);
        tearDownShapeRenderer();
    }

    private void setUpShapeRenderer(Batch batch) {
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin();
        Gdx.gl20.glLineWidth(3);
        shape.setColor(Color.WHITE);
    }


    private void tearDownShapeRenderer() {
        shape.end();
    }

    private void setUpBatch(Batch batch, float parentAlpha) {
        batch.begin();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    }

    private void drawInjured(Batch batch) {
        if (isInjured()) {
            if (!injuredAnimation.isAnimationFinished(animationTime))
                batch.draw(getCurrentFrame(injuredAnimation), getX(), getY(), Cell.SIZE, Cell.SIZE);
            else
                batch.draw(getCurrentFrame(burningAnimation, true), getX(), getY(), Cell.SIZE, Cell.SIZE);
        }
    }


    private TextureRegion getCurrentFrame(Animation animation) {
        return getCurrentFrame(animation, animationTime, false);
    }
    private TextureRegion getCurrentFrame(Animation animation, boolean looping) {
        return getCurrentFrame(animation, animationTime, looping);
    }

    private TextureRegion getCurrentFrame(Animation animation, float time, boolean looping) {
        return (TextureRegion) animation.getKeyFrame(time, looping);
    }


    private void drawKilled(Batch batch) {
        if (isKilled())
            batch.draw(getCurrentFrame(explosionAnimation), getX(), getY(), Cell.SIZE, Cell.SIZE);

    }

    private void drawMissed(Batch batch) {
        if (isMissed()) {
            batch.draw(getCurrentFrame(missAnimation, missAnimation.getFrameDuration() * 3, false),getX(),getY(),Cell.SIZE,Cell.SIZE);
            if (!missAnimation.isAnimationFinished(animationTime)) {
                batch.draw(getCurrentFrame(missAnimation), getX(), getY(), Cell.SIZE, Cell.SIZE);
            }
        }
    }


    private void tearDownBatch(Batch batch) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, 1f);
    }


    @Override
    public void drawDebug(ShapeRenderer shapes) {
        if (isHealthy()) {
            shapes.setColor(Color.GREEN);
            shapes.circle(getX() + SIZE / 2, getY() + SIZE / 2, SIZE / 2 - 3);
        }
    }


    public boolean isMissed() {
        return status == CellStatus.MISS;
    }


    public boolean isHealthy() {
        return status == CellStatus.HEALTHY;
    }

    public boolean isInjured() {
        return status == CellStatus.INJURED;
    }

    public boolean isSea() {
        return status == CellStatus.SEA;
    }

    public boolean isKilled() {
        return status == CellStatus.KILLED;
    }

    public void setSea() {
        this.status = CellStatus.SEA;
    }

    public void setMiss() {
        animationTime = 0;
        this.status = CellStatus.MISS;
    }

    public void setHealthy() {
        this.status = CellStatus.HEALTHY;
    }

    public void setInjured() {
        animationTime = 0;
        this.status = CellStatus.INJURED;
    }

    public void setKilled() {
        animationTime = 0;
        this.status = CellStatus.KILLED;
    }

    public Ship getShip() {
        return ship;
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
