package com.r3nny.seabattle.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorTest extends Actor {
    Texture texture;
    int x;
    int y;

    public ActorTest(Texture texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, x,y,texture.getWidth(), texture.getHeight());
    }
}
