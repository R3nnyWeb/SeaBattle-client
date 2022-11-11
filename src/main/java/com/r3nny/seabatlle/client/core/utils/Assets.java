package com.r3nny.seabatlle.client.core.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private final AssetManager manager;

    public Assets() {
        manager = new AssetManager();

    }

    public void loadAllAssets(){
        manager.load("mainMenu/bg.png", Texture.class);
    }

    public void unloadMenuAssets(){
        manager.unload("mainMenu/bg.png");
    }

    public Texture getMenuBackground(){
        return manager.get("mainMenu/mainMenu.png");
    }

    public boolean update(){
       return manager.update();
    }

}
