/* Nikita Vashkulatov(C) 2022 */
package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.r3nny.seabatlle.client.core.StarBattle;

import java.util.Random;

/**Включение звуков и музыки*/
public class SoundManager {
    private final Random random = new Random();

    private final Music mainMusic;
    private final Music battleMusic;
    private final Music wonMusic;
    private final Music loseMusic;

    private final Sound newGameClick;
    private final Sound focusSound;
    private final Sound shipEnterSound;
    private final Sound clickSound;
    private final Sound missSound_1;
    private final Sound missSound_2;
    private final Sound injuredSound_1;
    private final Sound injuredSound_2;
    private final Sound killedSound;
    private final float musicVolume = 0.1F;
    private final float soundVolume = 0.8F;

    public SoundManager() {
        this.wonMusic = StarBattle.assetsManager.getWonMusic();
        this.loseMusic = StarBattle.assetsManager.getLoseMusic();
        this.mainMusic = StarBattle.assetsManager.getMainMusic();
        this.battleMusic = StarBattle.assetsManager.getBattleMusic();
        this.newGameClick = StarBattle.assetsManager.getNewGameClickSound();
        this.focusSound = StarBattle.assetsManager.getFocusSound();
        this.shipEnterSound = StarBattle.assetsManager.getShipEnterSound();
        this.clickSound = StarBattle.assetsManager.getClickSound();
        this.missSound_1 = StarBattle.assetsManager.getMissSound_1();
        this.missSound_2 = StarBattle.assetsManager.getMissSound_2();
        this.injuredSound_1 = StarBattle.assetsManager.getInjuredSound_1();
        this.injuredSound_2 = StarBattle.assetsManager.getInjuredSound_2();
        this.killedSound = StarBattle.assetsManager.getKilledSound();
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
    public void playWonMusic() {
        wonMusic.setVolume(musicVolume);
        wonMusic.play();
    }

    public void stopWonMusic() {
        wonMusic.stop();
    }
    public void playLoseMusic() {
        loseMusic.setVolume(musicVolume);
        loseMusic.play();
    }

    public void stopLoseMusic() {
        loseMusic.stop();
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


    public void playFocusButton() {
        focusSound.play(soundVolume);
    }

    public void playClickSound() {
        clickSound.play(soundVolume);
    }

    public void playMissSound() {

        if (random.nextBoolean()) {
            missSound_1.play(soundVolume);
        } else {
            missSound_2.play(soundVolume);
        }
    }

    public void playInjuredSound() {
        if (random.nextBoolean()) {
            injuredSound_1.play(soundVolume);
        } else {
            injuredSound_2.play(soundVolume);
        }
    }



    public void playKilledSound() {
        killedSound.play(soundVolume);
    }
}
