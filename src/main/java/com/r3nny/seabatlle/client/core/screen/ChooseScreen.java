package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.SeaBattle;

public class ChooseScreen implements Screen {
    private Image bgImage;
    private ImageButton singleGame;
    private ImageButton multiGame;

    private SeaBattle game;
    private Stage stage;

    public ChooseScreen() {
        game = ((SeaBattle) Gdx.app.getApplicationListener());
        stage = SeaBattle.setUpStage();
        bgImage = new Image(SeaBattle.assetsManager.getMenuBackground());
        bgImage.setSize(SeaBattle.WORLD_WIDTH, SeaBattle.WORLD_HEIGHT);

        singleGame = new ImageButton(SeaBattle.assetsManager.getChooseButtonSkin());
        multiGame = new ImageButton(SeaBattle.assetsManager.getChooseButtonSkin(), "multiPlayer");


        singleGame.setSize(261, 257);
        multiGame.setSize(singleGame.getWidth(), singleGame.getHeight());
        //TODO: Вынести анимации
        Action fadeInAction = Actions.sequence(Actions.alpha(0F), Actions.fadeIn(1F));


        singleGame.setX(SeaBattle.WORLD_WIDTH / 2 - 25 - singleGame.getWidth());
        multiGame.setX(SeaBattle.WORLD_WIDTH / 2 + 25);

        singleGame.setY(SeaBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);
        multiGame.setY(SeaBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);

        TextButton backButton = new TextButton("Back to menu", SeaBattle.assetsManager.getMenuButtonSkin());
        backButton.setX(10);
        backButton.setSize(200, 50);
        backButton.setY(SeaBattle.WORLD_HEIGHT - 10 - backButton.getHeight());


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                multiGame.addAction(Actions.fadeOut(0.5F));
                backButton.addAction(Actions.fadeOut(0.5F));
                //TODO: Использовать фунцкионгальный интерфейс для анимации при любом перееходе
                singleGame.addAction(Actions.sequence(
                        Actions.fadeOut(0.5F),
                        Actions.run(() -> {
                            stage.clear();
                            game.setScreen(new MenuScreen());
                        })));
            }
        });

        singleGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SeaBattle.soundManager.stopMainMusic();
                SeaBattle.soundManager.playNewGameSound();
                game.setScreen(new ShipsCreatingScreen());
            }
        });
        singleGame.addAction(fadeInAction);
        multiGame.addAction(fadeInAction);
        backButton.addAction(fadeInAction);
        stage.addActor(bgImage);
        stage.addActor(singleGame);
        stage.addActor(multiGame);
        stage.addActor(backButton);


    }

    @Override
    public void show() {

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
