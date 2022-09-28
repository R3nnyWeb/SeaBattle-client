package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.controller.CellController;
import com.r3nny.seabattle.client.model.Cell;

public class CellView extends Actor {

    private CellController cellController;

    private Cell cell;
    private float x;
    private float y;
    public static final float SIZE = 25;
    private ShapeRenderer shape;

    public CellView(final Cell cell, float x, float y) {
        this.cell = cell;
        this.x = x;
        this.y = y;
        this.setBounds(x, y, SIZE,  SIZE);
        cellController = new CellController();
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cellController.handleCellClick(cell);
                return true;
            }
        });
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin();
        shape.setColor(Color.BLACK);
        shape.rect(x, y, SIZE, SIZE);
        shape.end();
    }



}
