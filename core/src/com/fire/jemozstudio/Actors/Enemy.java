/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fire.jemozstudio.Main;
import com.fire.jemozstudio.View.GameScreen;


public class Enemy extends Actor {

    public enum Status{live, death, stop}
    private  Status status = Status.live;
    private int Health, Score, Rank;
    public boolean over;
    Player player;
    public float rotation = 0;
    Main main;
    public Enemy(TextureRegion img, Vector2 position, int Rank, Player player, Main main) {
        super(img, position);
        this.Rank = Rank;
        this.player = player;
        this.main = main;
        switch (Rank){
            case 1:R = Main.WIDTH/30; Speed = 15; Score = 5; Health = 10; break;
            case 2:R = Main.WIDTH/27; Speed = 13; Score = 10; Health = 20; break;
            case 3:R = Main.WIDTH/25; Speed = 11; Score = 15; Health = 30; break;
            case 4:R = Main.WIDTH/23; Speed = 7; Score = 20; Health = 40; break;
            case 5:R = Main.WIDTH/19; Speed = 4; Score = 25; Health = 60; break;

            default:R = Main.WIDTH/25; Speed = 13; Score = Health = 10; break;
        }
        bounds = new Circle(position, R);

        direction.x = ((float) Math.sin(Math.toRadians(Math.random() * 360)));
        direction.y = ((float) Math.cos(Math.toRadians(Math.random() * 360)));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(img, position.x - R, position.y - R, R, R, R * 2, R * 2, 1, 1, rotation);
    }

    @Override
    public void update() {
        life(main);
        if((position.x + R > GameScreen.camera.position.x + GameScreen.camera.viewportWidth) || position.x + R > 6400)  playerPos(position, player);//direction.setX(-direction.getX());
        if((position.x - R < GameScreen.camera.position.x - GameScreen.camera.viewportWidth) || position.x - R < 0) playerPos(position, player);//direction.setX(-direction.getX());
        if((position.y + R > GameScreen.camera.position.y + GameScreen.camera.viewportHeight) || position.y + R > 3200) playerPos(position, player);//direction.setY(-direction.getY());
        if((position.y - R < GameScreen.camera.position.y - GameScreen.camera.viewportHeight) || position.y - R < 0) playerPos(position, player);//direction.setY(-direction.getY());

       if(!player.isGhost()) position.add(direction.x * Speed, direction.y * Speed);
        bounds.setPosition(position);
    }

    public boolean isOver() { return over; }
    public void setOver(boolean over) { this.over = over; }

    public void playerPos (Vector2 point, Player player) {
        float dx = player.position.x - point.x;
        float dy = player.position.y - point.y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        double angle = Math.atan(dy/dx)*180/Math.PI;
        //угол будет в диапозоне [-90;90]. Удобнее работать, если он в диапозоне [0;360]
        //поэтому пошаманим немного
        if(angle>0 &&dy<0)
            angle+=180;
        if(angle <0)
            if(dx<0)
                angle=180+angle;
            else
                angle+=360;

        rotation = (float) angle;
        direction.set((dx/dist), (dy/dist));
    }

    public Status getStatus() {
        return status;
    }

    public void hit(){
        Health -=5;
    }

    public void life(Main main){
        if(Health <= 0) status = Status.death;
        else {
            for(int i = 0; i < GameScreen.bullets.size; i++) {
                if(bounds.overlaps(GameScreen.bullets.get(i).bounds)){
                    int s = MathUtils.random(0,14);
                    main.sounds.get(s).play(0.5f);
                    Health -=5;
                    player.addScore(Score);
                    GameScreen.bullets.removeIndex(i--);
                    /*switch (Rank){
                        case 1: Score +=5; break;
                        case 2: Score +=10; break;
                        case 3: Score +=15; break;
                        case 4: Score +=20; break;
                        case 5: Score +=25; break;
                        default: break;
                    }*/
                }
            }
        }
    }
}
