package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    //TODO: Вынести пути в файл
    private final String MAIN_MENU_BG_PATH = "mainMenu/bg.png";
    private final String ONE_DECK_SHIP_PATH = "ships/1xShip.png";
    private final String TWO_DECK_SHIP_PATH = "ships/3xShip.png";
    private final String THREE_DECK_SHIP_PATH = "ships/3xShip.png";
    private final String FOUR_DECK_SHIP_PATH = "ships/4xShip.png";
    private final String IN_GAME_BACKGROUND = "inGame/bg.jpg";

    private final String MENU_BUTTON_SKIN = "menuButtons/skin.json";
    private final String CHOOSE_BUTTON_SKIN = "chooseButton/button.json";
    private final String MENU_BUTTON_ATLAS = "menuButtons/skin.atlas";
    private final String CHOOSE_BUTTON_ATLAS = "chooseButton/button.atlas";


    private final AssetManager manager;


    public Assets() {
        manager = new AssetManager();

    }

    public void loadAllAssets() {
        manager.load(MAIN_MENU_BG_PATH, Texture.class);
        manager.load(ONE_DECK_SHIP_PATH, Texture.class);
        manager.load(TWO_DECK_SHIP_PATH, Texture.class);
        manager.load(THREE_DECK_SHIP_PATH, Texture.class);
        manager.load(FOUR_DECK_SHIP_PATH, Texture.class);
        manager.load(IN_GAME_BACKGROUND, Texture.class);


        manager.load(MENU_BUTTON_SKIN, Skin.class);
        manager.load(CHOOSE_BUTTON_SKIN, Skin.class);


        manager.load(MENU_BUTTON_ATLAS, TextureAtlas.class);
        manager.load(CHOOSE_BUTTON_ATLAS, TextureAtlas.class);
    }

    public void unloadMenuAssets() {
        manager.unload(MAIN_MENU_BG_PATH);
        manager.unload(ONE_DECK_SHIP_PATH);
        manager.unload(TWO_DECK_SHIP_PATH);
        manager.unload(THREE_DECK_SHIP_PATH);
        manager.unload(FOUR_DECK_SHIP_PATH);
        manager.unload(IN_GAME_BACKGROUND);
    }

    public Texture getMenuBackground() {
        return manager.get(MAIN_MENU_BG_PATH);
    }

    public Texture getOneDeckShip() {
        return manager.get(ONE_DECK_SHIP_PATH);
    }

    public Texture getTwoDeckShip() {
        return manager.get(TWO_DECK_SHIP_PATH);
    }

    public Texture getThreeDeckShip() {
        return manager.get(THREE_DECK_SHIP_PATH);
    }

    public Texture getFourDeckShip() {
        return manager.get(FOUR_DECK_SHIP_PATH);
    }

    public Texture getInGameBackground() {
        return manager.get(IN_GAME_BACKGROUND);
    }

    public Skin getMenuButtonSkin() {
        Skin skin = manager.get(MENU_BUTTON_SKIN);
        skin.addRegions(manager.get(MENU_BUTTON_ATLAS));
        return skin;
    }
    public Skin getChooseButtonSkin() {
        Skin skin = manager.get(CHOOSE_BUTTON_SKIN);
        skin.addRegions(manager.get(CHOOSE_BUTTON_ATLAS));
        return skin;
    }

    public boolean update() {
        return manager.update();
    }

}
