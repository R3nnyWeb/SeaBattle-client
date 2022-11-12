package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.r3nny.seabatlle.client.core.SeaBattle;

import java.util.Random;

public class SoundManager {
    private Random random = new Random();

    private final Music mainMusic;
    private final Music battleMusic;

    private final Sound newGameClick;
    private final Sound focusSound;
    private final Sound shipEnterSound;
    private final Sound clickSound;
    private final Sound missSound_1;
    private final Sound missSound_2;
    private final Sound injuredSound_1;
    private final Sound injuredSound_2;
    private final Sound killedSound;


    //TODO : Файл настроек
    private float musicVolume = 0.1F;
    private float soundVolume = 0.8F;

    public SoundManager() {

        this.mainMusic = SeaBattle.assetsManager.getMainMusic();
        this.battleMusic = SeaBattle.assetsManager.getBattleMusic();
        this.newGameClick = SeaBattle.assetsManager.getNewGameClickSound();
        this.focusSound = SeaBattle.assetsManager.getFocusSound();
        this.shipEnterSound = SeaBattle.assetsManager.getShipEnterSound();
        this.clickSound = SeaBattle.assetsManager.getClickSound();
        this.missSound_1 = SeaBattle.assetsManager.getMissSound_1();
        this.missSound_2 = SeaBattle.assetsManager.getMissSound_2();
        this.injuredSound_1 = SeaBattle.assetsManager.getInjuredSound_1();
        this.injuredSound_2 = SeaBattle.assetsManager.getInjuredSound_2();
        this.killedSound = SeaBattle.assetsManager.getKilledSound();
        mainMusic.setLooping(true);
        battleMusic.setLooping(true);
    }

    public void playMainMusic() {
        mainMusic.setVolume(musicVolume);
        mainMusic.play();
    }

    public void stopMainMusic() {
        mainMusic.stop();
    }

    public void playBattleMusic() {
        battleMusic.setVolume(musicVolume);
        battleMusic.play();
    }

    public void stopBattleMusic() {
        battleMusic.stop();
    }

    public void playNewGameSound() {
        newGameClick.play(soundVolume);
    }

    public void playShipEnterSound() {
        shipEnterSound.play(soundVolume);
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void playFocusButton() {
        focusSound.play(soundVolume);
    }

    public void playClickSound() {
        clickSound.play(soundVolume);
    }

    public void  playMissSound(){

        if (random.nextBoolean()) {
            missSound_1.play(soundVolume);
        } else{
            missSound_2.play(soundVolume);
        }

    }


    public void playInjuredSound(){
        if (random.nextBoolean()) {
            injuredSound_1.play(soundVolume);
        } else{
            injuredSound_2.play(soundVolume);
        }
    }

    public void setMusicVolume(float musicVolume) {
        if (musicVolume > 0.2F || musicVolume < 0F) {
            Gdx.app.error("Music manager", "Incorrect Volume sound: " + musicVolume);
            throw new IllegalArgumentException("Incorrect Volume");
        } else this.musicVolume = musicVolume;
    }


    public void playKilledSound() {
        killedSound.play(soundVolume);
    }
}