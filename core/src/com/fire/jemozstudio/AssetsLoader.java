/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetsLoader {
    public AssetManager manager = new AssetManager();

    public static final String actText = "objects/textures/actors.atlas";
    public static final String uiText = "ui/ui.atlas";
    public static final String joyText = "objects/joystick/joystick.atlas";
    public static final String butText = "objects/buttons/buttons.atlas";
    public static final String bloodText = "objects/textures/blood.atlas";
    public static final String gunSound = "raw/gun_sound.mp3";

    public AssetsLoader(){loadAssets();}

    public void loadAssets(){
        //Загрузчик для карты
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/Arena.tmx", TiledMap.class);
        //Загрузчик для текстурок)))
        manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));
        manager.load(actText, TextureAtlas.class);
        manager.load(uiText, TextureAtlas.class);
        manager.load(joyText, TextureAtlas.class);
        manager.load(butText, TextureAtlas.class);
        manager.load(bloodText, TextureAtlas.class);

        manager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        manager.load("objects/textures/panel.9.png", Texture.class);
        //Загрузчик для звуков
        manager.setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
        for (int i = 1; i < 16; i++) manager.load("raw/man_death_"+ i +".ogg", Sound.class);
        manager.load(gunSound, Sound.class);

        manager.finishLoading();
// когда AssetManager закончил загрузку
     //   TiledMap map = assetManager.get("level1.tmx");
    }


}
