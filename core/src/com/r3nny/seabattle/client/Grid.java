package com.r3nny.seabattle.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.r3nny.seabattle.client.SeaBattle.WORLD_HEIGHT;
import static com.r3nny.seabattle.client.SeaBattle.WORLD_WIDTH;

public class Grid extends Actor {
    ShapeRenderer shape;

    public Grid() {

        shape = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        for (int i = 0; i < SeaBattle.WORLD_HEIGHT / 16; i++) {
            shape.line(0, i * 16, WORLD_WIDTH, i * 16);
        }
        for (int i = 0; i < WORLD_WIDTH / 16; i++) {
            shape.line(i * 16, 0, i * 16, SeaBattle.WORLD_HEIGHT);
        }

        shape.setColor(Color.BLACK);
        shape.line(WORLD_WIDTH / 2, 0, WORLD_WIDTH / 2, WORLD_HEIGHT);
        shape.line(0, WORLD_HEIGHT/2, WORLD_WIDTH, WORLD_HEIGHT/2);
        shape.end();
    }
}
