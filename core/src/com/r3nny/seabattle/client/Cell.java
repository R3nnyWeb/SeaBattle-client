package com.r3nny.seabattle.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Cell extends Actor {
    private float x;
    private float y;
    private float size;
    private int fieldX;
    private int fieldY;
    private Ship ship;
    private Color color;
    private ShapeRenderer shape;


    public Cell(float x, float y, float size, final int fieldX, final int fieldY, Ship ship) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.ship = ship;
        this.color = Color.BLACK;
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
        this.setBounds(x, y, x + size, y + size);

        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(fieldX + " " + fieldY);
                color = Color.WHITE;
                return true;
            }


        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin();
        shape.setColor(color);
        shape.rect(x,y,size,size);
        shape.end();
    }


}
