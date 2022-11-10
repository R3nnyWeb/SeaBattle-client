package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.acanthite.gdx.graphics.g2d.FreeTypeSkinLoader;
import com.r3nny.seabatlle.client.core.SeaBattle;


public class MenuScreen implements Screen {

    //TODO: Dынести все??
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture bg;

    public MenuScreen(Stage stage){
        this.stage = stage;
        batch = new SpriteBatch();
        bg = new Texture("menu_bg.png");




        //TODO: Убери к черту
        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(Skin.class, new FreeTypeSkinLoader(assetManager.getFileHandleResolver()));
        assetManager.load("button/skin.json", Skin.class);

        Skin skin = assetManager.get("button/skin.json");
        skin.addRegions(new TextureAtlas("button/skin.atlas"));

    }

    @Override
    public void show() {
        Gdx.app.log( "Showing Game Screen", "screen");

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
        batch.begin();
        batch.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        batch.draw(bg, 0, 0, SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
