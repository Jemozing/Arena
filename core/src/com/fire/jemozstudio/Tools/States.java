/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.fire.jemozstudio.Actors.Player;
import com.fire.jemozstudio.Main;

public class States extends Actor {
    TextureRegion hp_scale, hp;
    float hitpoint;
    float w, h;
    Player player;
    public  States(TextureRegion hp_scale, TextureRegion hp, Player player){
        this.hp_scale = hp_scale;
        this.hp = hp;
        this.player = player;
        w = hp.getRegionWidth();
        h = hp.getRegionHeight();
    }

    public void update(){
        hitpoint = player.getHealth() / 10;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(204, 51,51,1);
        batch.draw(hp_scale, 500, Main.HEIGHT - Main.HEIGHT - Main.HEIGHT / 8 - hp_scale.getRegionHeight(), hp_scale.getRegionWidth() * 2, hp_scale.getRegionHeight() * 2);
        batch.draw(hp,Main.WIDTH / 100 + 3, Main.HEIGHT - Main.HEIGHT - Main.HEIGHT / 8 - hp_scale.getRegionHeight() + 3,  w / 10 * hitpoint - 6,  h -6);
    }
}
