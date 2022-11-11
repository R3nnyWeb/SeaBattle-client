package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.Game;
import com.r3nny.seabatlle.client.core.GameStatus;
import com.r3nny.seabatlle.client.core.SeaBattle;
import com.r3nny.seabatlle.client.core.utils.Assets;
import com.ray3k.stripe.FreeTypeSkin;


public class MenuScreen implements Screen {

    //TODO: Dынести все??
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture bg;



    private final SeaBattle game;

    public MenuScreen() {

        game = ((SeaBattle) Gdx.app.getApplicationListener());
        Game.status = GameStatus.MENU;
        this.stage = game.stage;
        batch = new SpriteBatch();
        bg = new Texture("mainMenu/bg.png");

        //TODO: Убери к черту
        Label.LabelStyle label1Style = new Label.LabelStyle();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("buttons/minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 26;
        parameter.characters = "круто   STAR WARS";
        label1Style.font = generator.generateFont(parameter);
        ;
        label1Style.fontColor = Color.WHITE;
        Label label1 = new Label(" круто   STAR WARS", label1Style);
        label1.setPosition(0, 0);
        stage.addActor(label1);




        Skin skin = new FreeTypeSkin(Gdx.files.internal("buttons/skin.json"));
        skin.addRegions(new TextureAtlas("buttons/skin.atlas"));
        TextButton start = new TextButton("New game", skin);
        start.setX(SeaBattle.WORLD_WIDTH - 350);
        start.setY(100);
        //TODO: Listeners
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                game.setScreen(new SingleGameScreen(game.stage));
            }
        });
        //TODO: Плавные переходы. Разделить stage
        game.stage.addAction(Actions.sequence( Actions.fadeIn(0.2F)));
        stage.addActor(start);
    }

    @Override
    public void show() {
        Gdx.app.log("Menu Screen", "Showing menu screen");

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
