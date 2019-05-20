/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fire.jemozstudio.View.GameScreen;

public class Bullet extends Actor{
    public boolean isOut;
    public Bullet(TextureRegion img, Vector2 position, float Speed, float R, Vector2 direction){
        super(img, position, Speed, R);
        this.direction = new Vector2(direction);
    }

    @Override
    public void draw(SpriteBatch batch){
        batch.draw(img, position.x - R, position.y - R, R*2, R*2);
    }

    @Override
    public void update(){
        isOut = ((position.x + R > GameScreen.camera.position.x + GameScreen.camera.viewportWidth)
                || (position.x - R < GameScreen.camera.position.x - GameScreen.camera.viewportWidth)
                || (position.y + R > GameScreen.camera.position.y + GameScreen.camera.viewportHeight)
                || (position.y - R < GameScreen.camera.position.y - GameScreen.camera.viewportHeight))?true:false;

        position.add(direction.x * Speed, direction.y * Speed);
        bounds.setPosition(position);
    }
}
