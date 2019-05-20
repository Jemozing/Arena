/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends Actor {

    private  int Score;
    private int health;
    float rotation = 0;
    private boolean ghost = false;
    private long StartTimer = 0;
    public Player(TextureRegion img, Vector2 position, float speed, float R, int health) {
        super(img, position, speed, R);
        this.health = health;
    }

    public int getHealth(){
        return health;
    }
    public void setHealth(float health) {
        this.health -= health;
    }
    public int getScore() { return Score; }
    public void addScore(int Score){this.Score += Score;}
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public boolean isGhost() { return ghost; }

    @Override
    public void draw(SpriteBatch batch) {
        if(ghost) batch.setColor(1,1,1,1);
        batch.draw(img, position.x - R, position.y - R, img.getRegionWidth(), img.getRegionHeight(),  img.getRegionWidth()*2, img.getRegionHeight()*2, 1, 1, rotation);
    }

    public void hit(int i){
        if(!ghost) {  health -=i; }
        if(health <= 0) ghost = true;
    }

    @Override
    public void update() {
        if(position.x + R > 6400) position.x = (6400-R);
        if(position.x - R < 0) position.x = (R);
        if(position.y + R > 3200) position.y = (3200 - R);
        if(position.y - R < 0)position.y = (R);
        bounds.setPosition(position);
        if(!ghost) position.add(direction.x * Speed, direction.y * Speed);
    }
}
