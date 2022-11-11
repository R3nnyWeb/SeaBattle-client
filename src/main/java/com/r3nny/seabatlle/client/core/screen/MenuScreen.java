package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
    private final Texture bgTexture;
    private final Image bgImage;

    private final SeaBattle game;
    private final Assets manager;

    public MenuScreen() {
        this.manager = SeaBattle.manager;
        game = ((SeaBattle) Gdx.app.getApplicationListener());
        Game.status = GameStatus.MENU;
        this.stage = SeaBattle.setUpStage();
        batch = new SpriteBatch();
        bgTexture = manager.getMenuBackground();
        bgImage = new Image(bgTexture);
        bgImage.setSize(SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT);
//        //TODO: Убери к черту
//        Label.LabelStyle label1Style = new Label.LabelStyle();
//
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("buttons/minecraft.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 26;
//        parameter.characters = "круто   STAR WARS";
//        label1Style.font = generator.generateFont(parameter);
//        ;
//        label1Style.fontColor = Color.WHITE;
//        Label label1 = new Label(" круто   STAR WARS", label1Style);
//        label1.setPosition(0, 0);
//        stage.addActor(label1);






        TextButton start = new TextButton("New game", SeaBattle.manager.getMenuButtonSkin());
        TextButton end = new TextButton("Exit", SeaBattle.manager.getMenuButtonSkin());

        start.setX(SeaBattle.WORLD_WIDTH - 350);
        start.setSize(300, 50);
        end.setSize(300, 50);
        end.setX(start.getX());
        start.setY(100);
        end.setY(start.getY() - 60);
        //TODO: Listeners
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                start.addAction(Actions.fadeOut(0.5F));
                end.addAction(Actions.sequence(Actions.fadeOut(0.5F), Actions.run(new Runnable() {
                    @Override
                    public void run() {
//                        stage.clear();
                        game.setScreen(new ChooseScreen());
                    }
                })));
            }
        });
        end.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //TODO: Плавные переходы. Разделить stage
        stage.addAction(Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F)));
        stage.addActor(bgImage);

        stage.addActor(start);
        stage.addActor(end);
    }

    @Override
    public void show() {
        Gdx.app.log("Menu Screen", "Showing menu screen");

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(Color.BLACK));
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