package com.r3nny.seabattle.client.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.r3nny.seabattle.client.Game;
import com.r3nny.seabattle.client.GameStatus;
import com.r3nny.seabattle.client.controller.CellController;
import com.r3nny.seabattle.client.controller.ShipsCreator;
import com.r3nny.seabattle.client.model.Cell;
import com.r3nny.seabattle.client.model.Ship;
import com.r3nny.seabattle.client.model.ShipType;

import static com.r3nny.seabattle.client.controller.ShipsCreator.currentShipType;
import static com.r3nny.seabattle.client.controller.ShipsCreator.shipTypes;

public class CellView extends Actor {

    private CellController cellController;

    private Cell cell;
    private float x;
    private float y;
    public static final float SIZE = 23;

    private ShipView shipPreview;

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
                Game.playerView.removeShipPreview(shipPreview);
                return true;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
               if ((Game.status == GameStatus.SHIPS_STAGE) && (ShipsCreator.canCreateShipHere(cell,Game.playerField.getField()))){
                   Cell[] temp = {cell};
                   shipPreview = Game.playerView.addPreViewShip(new Ship(temp, ShipsCreator.shipTypes[currentShipType]));
               }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (Game.status == GameStatus.SHIPS_STAGE){
                   Game.playerView.removeShipPreview(shipPreview);
                }
                shipPreview = null;
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
