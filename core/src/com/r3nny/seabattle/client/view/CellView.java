package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.controller.CellController;
import com.r3nny.seabattle.client.controller.ShipsCreator;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.CellStatus;
import com.r3nny.seabattle.client.model.Ship;

import static com.r3nny.seabattle.client.controller.ShipsCreator.currentShipType;

public class CellView extends Actor {

    public static final float SIZE = 23;
    private CellController cellController;
    private Cell cell;
    private float x;
    private float y;

    private ShapeRenderer shape;

    public CellView(final Cell cell, float x, float y) {
        this.cell = cell;
        this.x = x;
        this.y = y;
        this.setBounds(x, y, SIZE, SIZE);

        cellController = new CellController();

        this.addListener(new InputListener() {


            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    cellController.handleCellClick(cell);
                    Game.playerView.removeShipPreview();

                }
                Game.playerView.removeShipPreview();
                return true;
            }



            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if ((Game.status == GameStatus.SHIPS_STAGE) && (ShipsCreator.canCreateShipHere(cell, Game.playerField.getField()))) {
                    Cell[] temp = {cell};
                    Game.playerView.addPreViewShip(new Ship(temp, ShipsCreator.shipTypes[currentShipType]));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (Game.status == GameStatus.SHIPS_STAGE) {
                    Game.playerView.removeShipPreview();
                }
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
        if(cell.getStatus() == CellStatus.NOT_ALLOWED){
            shape.setColor(Color.RED);
            shape.circle(x + SIZE/2, y + SIZE/2, SIZE/2 -3);
        }
        shape.end();
    }


}
