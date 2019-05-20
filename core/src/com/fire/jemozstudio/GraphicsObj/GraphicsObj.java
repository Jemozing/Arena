/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.GraphicsObj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GraphicsObj {
    public TextureRegion img;

    public GraphicsObj(TextureRegion img){
        this.img = img;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update();
}
