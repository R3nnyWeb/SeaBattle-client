/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.utils;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    // TODO: Вынести пути в файл и разбить на листы по классам
    private final String MAIN_MENU_BG_PATH = "mainMenu/bg.png";
    private final String ONE_DECK_SHIP_PATH = "ships/1xShip.png";
    private final String TWO_DECK_SHIP_PATH = "ships/2xShip.png";
    private final String THREE_DECK_SHIP_PATH = "ships/3xShip.png";
    private final String FOUR_DECK_SHIP_PATH = "ships/4xShip.png";
    private final String IN_GAME_BACKGROUND = "inGame/bg.jpg";

    private final String GAME_LOGO = "gameLogo.png";

    private final String MENU_BUTTON_SKIN = "menuButtons/skin.json";
    private final String CHOOSE_BUTTON_SKIN = "chooseButton/button.json";
    private final String MENU_BUTTON_ATLAS = "menuButtons/skin.atlas";
    private final String CHOOSE_BUTTON_ATLAS = "chooseButton/button.atlas";

    private final String MAIN_MUSIC = "sounds/mainMusic.mp3";
    private final String BATTLE_MUSIC = "sounds/battleMusic.mp3";

    private final String NEW_GAME_CLICK = "sounds/newGameClick.mp3";
    private final String FOCUS_SOUND = "sounds/focus.WAV";
    private final String SHIP_ENTER_SOUND = "sounds/shipEnter.WAV";
    private final String CLICK_SOUND = "sounds/click.WAV";
    private final String MISS_SOUND_1 = "sounds/miss_1.mp3";
    private final String MISS_SOUND_2 = "sounds/miss_2.mp3";

    private final String SHIP_INJURED_SOUND_1 = "sounds/injured_1.wav";
    private final String SHIP_INJURED_SOUND_2 = "sounds/injured_2.wav";
    private final String KILLED_SOUND = "sounds/killed.WAV";

    private final String INJURED_ANIMATION = "sprites/injuredSprite.png";
    private final String BURNING_ANIMATION = "sprites/burningSprite.png";
    private final String MISS_ANIMATION = "sprites/missSprite.png";

    private final String EXPLOSION_ANIMATION = "sprites/explosionSprite.png";
    private final String EXPLOSION_ATLAS = "sprites/explosionAtlas.atlas";

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
        manager.load(GAME_LOGO, Texture.class);
        manager.load(INJURED_ANIMATION, Texture.class);
        manager.load(BURNING_ANIMATION, Texture.class);
        manager.load(MISS_ANIMATION, Texture.class);
        manager.load(EXPLOSION_ANIMATION, Texture.class);

        manager.load(MENU_BUTTON_SKIN, Skin.class);
        manager.load(CHOOSE_BUTTON_SKIN, Skin.class);

        manager.load(MENU_BUTTON_ATLAS, TextureAtlas.class);
        manager.load(CHOOSE_BUTTON_ATLAS, TextureAtlas.class);
        manager.load(EXPLOSION_ATLAS, TextureAtlas.class);

        manager.load(MAIN_MUSIC, Music.class);
        manager.load(BATTLE_MUSIC, Music.class);

        manager.load(NEW_GAME_CLICK, Sound.class);
        manager.load(SHIP_ENTER_SOUND, Sound.class);
        manager.load(FOCUS_SOUND, Sound.class);
        manager.load(CLICK_SOUND, Sound.class);
        manager.load(MISS_SOUND_1, Sound.class);
        manager.load(MISS_SOUND_2, Sound.class);
        manager.load(SHIP_INJURED_SOUND_1, Sound.class);
        manager.load(SHIP_INJURED_SOUND_2, Sound.class);
        manager.load(KILLED_SOUND, Sound.class);
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

    public Texture getMenuLogo() {
        return manager.get(GAME_LOGO);
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

    public Music getMainMusic() {
        return manager.get(MAIN_MUSIC);
    }

    public Music getBattleMusic() {
        return manager.get(BATTLE_MUSIC);
    }

    public Sound getNewGameClickSound() {
        return manager.get(NEW_GAME_CLICK);
    }

    public Sound getFocusSound() {
        return manager.get(FOCUS_SOUND);
    }

    public Sound getShipEnterSound() {
        return manager.get(SHIP_ENTER_SOUND);
    }

    public Sound getClickSound() {
        return manager.get(CLICK_SOUND);
    }

    public Sound getMissSound_1() {
        return manager.get(MISS_SOUND_1);
    }

    public Sound getMissSound_2() {
        return manager.get(MISS_SOUND_2);
    }

    public Sound getInjuredSound_1() {
        return manager.get(SHIP_INJURED_SOUND_1);
    }

    public Sound getInjuredSound_2() {
        return manager.get(SHIP_INJURED_SOUND_2);
    }

    public Texture getInjuredAnimation() {
        return manager.get(INJURED_ANIMATION);
    }

    public boolean update() {
        return manager.update();
    }

    public Sound getKilledSound() {
        return manager.get(KILLED_SOUND);
    }

    public Texture getBurningAnimation() {
        return manager.get(BURNING_ANIMATION);
    }

    public Texture getMissAnimation() {
        return manager.get(MISS_ANIMATION);
    }

    public Texture getExplosionAnimation() {
        return manager.get(EXPLOSION_ANIMATION);
    }

    public BitmapFont getFont(int size) {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters = DEFAULT_CHARS;
        return generator.generateFont(parameter);
    }
}
