/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.fire.jemozstudio.Actors.Bullet;
import com.fire.jemozstudio.Main;
import com.fire.jemozstudio.View.GameScreen;

public class BulletGenerator {
    boolean isFire;
    long lastTime;

    public void update(Joystick joy, Main main){
        isFire = (joy.getDirection().x == 0 && joy.getDirection().y == 0)?false:true;
        if (TimeUtils.nanoTime() - lastTime > 400000000){
        if(isFire){ GameScreen.bullets.add(new Bullet(main.getJoystickAt().findRegion("2"), GameScreen.player.position, 27, GameScreen.player.R / 2, new Vector2(joy.getDirection().x, joy.getDirection().y)));
        main.gun.play(0.5f);}
        lastTime = TimeUtils.nanoTime();
        }

    }
}
