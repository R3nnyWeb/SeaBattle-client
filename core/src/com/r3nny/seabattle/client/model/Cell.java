package com.r3nny.seabattle.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.controller.GameController;

public class Cell extends Actor {

    public static final float SIZE = 31.37f;
//   public static final float SIZE = 32f;

    private final int column;
    private final int row;
    private Ship ship;
    private CellStatus status;

    private final ShapeRenderer shape;


    public Cell(float x, float y, int column, int row, Ship ship, CellStatus status) {
        this.column = column;
        this.row = row;
        this.ship = ship;
        super.setX(x);
        super.setY(y);

        this.status = status;
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
        this.setBounds(x, y, SIZE, SIZE);

        System.out.println( this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
               if(Game.status == GameStatus.PLAYER_TURN){
                   System.out.println( GameController.shoot(Cell.this));
               }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("up");
            }
        }));

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
            shape.circle(getX() + SIZE / 2, getY() + SIZE / 2, SIZE / 2 - 3);
        }
        shape.end();
        batch.begin();
//        batch.draw(new Texture("cell.png"),getX(),getY(),Cell.SIZE,Cell.SIZE);

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
        return "Cell{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
