/* Nikita Vashkulatov(C)2022 */
package com.r3nny.seabatlle.client.core.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.r3nny.seabatlle.client.core.StarBattle;

public class ShipsCreatingArea extends Group {

    private final ShapeRenderer shape;
    Label controlsHelp;
    Label fourDeskLabel;
    Label treeDeskLabel;
    Label twoDeskLabel;
    Label oneDeskLabel;

    public ShipsCreatingArea(float x, float y) {
        super.setPosition(x, StarBattle.WORLD_HEIGHT - y + 10);
        Label.LabelStyle skin = new Label.LabelStyle();

        skin.font = StarBattle.assetsManager.getFont(52);
        skin.fontColor = new Color(Color.WHITE);
        controlsHelp =
                new Label(
                        "Press A to automatically place ships\n\nDrag`n`Drop ship to place it. \n\nPress RMB to rotate ship",
                        skin);
        controlsHelp.setFontScale(0.25F);
        controlsHelp.setPosition(x + 10, getY() + 130);
        fourDeskLabel = new Label("Super Destroyers: ", skin);
        fourDeskLabel.setFontScale(0.25F);
        fourDeskLabel.setPosition(controlsHelp.getX(), controlsHelp.getY() + 50);
        treeDeskLabel = new Label("Destroyers: ", skin);
        treeDeskLabel.setFontScale(0.25F);
        treeDeskLabel.setPosition(fourDeskLabel.getX(), fourDeskLabel.getY() - 60);
        twoDeskLabel = new Label("Corvets: ", skin);
        twoDeskLabel.setFontScale(0.25F);
        twoDeskLabel.setPosition(treeDeskLabel.getX(), treeDeskLabel.getY() - 60);
        oneDeskLabel = new Label("Default: ", skin);
        oneDeskLabel.setFontScale(0.25F);
        oneDeskLabel.setPosition(twoDeskLabel.getX(), twoDeskLabel.getY() - 60);

        this.shape = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        Color color = getColor();
        shape.setColor(0, 0, 0, 0.7F);
        shape.rect(getX(), getY() - 20, Cell.SIZE * 10, Cell.SIZE * 10 + 10);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);
        shape.rect(getX(), getY() - 20, Cell.SIZE * 10, Cell.SIZE * 10 + 10);
        shape.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        controlsHelp.draw(batch, parentAlpha);
        fourDeskLabel.draw(batch, parentAlpha);
        treeDeskLabel.draw(batch, parentAlpha);
        twoDeskLabel.draw(batch, parentAlpha);
        oneDeskLabel.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
