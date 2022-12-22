/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.r3nny.seabatlle.client.core.StarBattle;

/**Экран с выбором режима игры*/
public class ChooseScreen extends InGameScreen {
    private ImageButton singleGame;
    private ImageButton multiGame;
    private Image bgImage;


    public ChooseScreen( ) {
        super(new Image (StarBattle.assetsManager.getMenuBackground()));
        setUpSingleGameButton();
        setUpMultiGameButton();

    }



    private void setUpSingleGameButton() {
        singleGame = new ImageButton(assetManager().getChooseButtonSkin());
        singleGame.setSize(261, 257);
        singleGame.setX(StarBattle.WORLD_WIDTH / 2 - 25 - singleGame.getWidth());
        singleGame.setY(StarBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);
        singleGame.addListener(
                new ClickListener() {
                    @Override
                    public void enter(
                            InputEvent event, float x, float y, int pointer, Actor fromActor) {
                       soundManager().playFocusButton();
                    }

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        soundManager().stopMainMusic();
                        soundManager().playNewGameSound();
                        application.setScreen(new ShipsCreatingScreen());
                    }
                });
        singleGame.addAction(animationManager().getFadeInAnimation());

        stage.addActor(singleGame);
    }

    private void setUpMultiGameButton() {
        multiGame = new ImageButton(assetManager().getChooseButtonSkin(), "multiPlayer");

        multiGame.setSize(singleGame.getWidth(), singleGame.getHeight());
        multiGame.setX(StarBattle.WORLD_WIDTH / 2 + 25);
        multiGame.setY(StarBattle.WORLD_HEIGHT / 2 - singleGame.getHeight() / 2);

        multiGame.addAction(animationManager().getFadeInAnimation());

        stage.addActor(multiGame);
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
