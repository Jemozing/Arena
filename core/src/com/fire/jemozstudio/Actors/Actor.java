/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.fire.jemozstudio.GraphicsObj.GraphicsObj;

public abstract class Actor extends GraphicsObj {

    public Vector2 position;
    public float Speed, R;
    public Circle bounds;
    public Vector2 direction;

    public Actor(TextureRegion img, Vector2 position, float speed, float R) {
        super(img);
        this.position = new Vector2(position);
        this.Speed = speed;
        this.R = R;
        bounds = new Circle(position, R);
        direction = new Vector2(0,0);
    }

    public Actor(TextureRegion img, Vector2 position) {
        super(img);
        this.position = new Vector2(position);
        bounds = new Circle(position, R);
        direction = new Vector2(0,0);
    }

    public  void  setDirection(Vector2 dir){
        direction = dir;
    }

}
