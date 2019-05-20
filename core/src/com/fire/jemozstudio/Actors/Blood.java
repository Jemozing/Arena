/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Blood extends Actor {
    float R;
    public Blood(TextureRegion img, Vector2 position, float R) {
        super(img, position);
        this.R = R;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(img, position.x - R/2, position.y - R/2, R,R);
    }

    @Override
    public void update() {

    }
}
