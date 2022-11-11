package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.r3nny.seabatlle.client.core.SeaBattle;

public class SoundManager {

    private final Music mainMusic;
    private final Music battleMusic;

    private final Sound newGameClick;

    //TODO : Файл настроек
    private float soundVolume = 0.1F;

    public SoundManager() {

        this.mainMusic = SeaBattle.assetsManager.getMainMusic();
        this.battleMusic = SeaBattle.assetsManager.getBattleMusic();
        this.newGameClick = SeaBattle.assetsManager.getNewGameClickSound();
        mainMusic.setLooping(true);
        battleMusic.setLooping(true);
    }

    public void playMainMusic() {
        mainMusic.setVolume(soundVolume);
         mainMusic.play();
    }

    public void stopMainMusic() {
        mainMusic.stop();
    }
    public void playBattleMusic() {
        battleMusic.setVolume(soundVolume);
        battleMusic.play();
    }

    public void stopBattleMusic() {
        battleMusic.stop();
    }

    public void playNewGameSound(){
        newGameClick.play(soundVolume);
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        if (soundVolume > 0.2F || soundVolume < 0F) {
            Gdx.app.error("Music manager", "Incorrect Volume sound: " + soundVolume);
            throw new IllegalArgumentException("Incorrect Volume");
        } else this.soundVolume = soundVolume;
    }

    public void playFocusButton() {
    }
}
