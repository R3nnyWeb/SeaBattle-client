/* (C)2022 */
package com.r3nny.seabatlle.client;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.r3nny.seabatlle.client.core.StarBattle;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        if (StarBattle.DEBUG) {
            config.setWindowedMode(1280, 720);
        } else {
            config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setTitle("Star Battle");
        new Lwjgl3Application(new StarBattle(), config);
    }
}
