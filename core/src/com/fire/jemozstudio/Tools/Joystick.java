/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.TimeUtils;
import com.fire.jemozstudio.Main;

public class Joystick extends Actor {
    Main main;
    TextureRegion CircleImg, StickImg;
    Circle CircleBounds, StickBounds;
    float Rcirle, Rstick;
    private  int pointer = -1;
    Vector2 direction;
    long time = 0;
    float rotation;
    public Joystick(Vector2 pos, Main main){
        this.main = main;
        Rcirle = Main.HEIGHT/3 / 2;
        Rstick = Rcirle / 2;
        CircleImg = main.getJoystickAt().findRegion("1");
        StickImg = main.getJoystickAt().findRegion("2");
        CircleBounds = new Circle(pos, Rcirle);
        StickBounds = new Circle(pos, Rstick);
        direction = new Vector2(0,0);
        setBounds(pos.x - Rcirle, pos.y - Rcirle, Rcirle * 2, Rcirle * 2);
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                atControl(x,y);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                //System.out.println("Game is Bugs! Please report to me)");
                atControl(x,y);
               /* if(TimeUtils.nanoTime() - time > 2000000000) {
                    System.out.println("point-x:" + x);
                    System.out.println("point-y:" + y);
                    time = TimeUtils.nanoTime();
                    System.out.println("stick-x:" + StickBounds.x);
                    System.out.println("stick-y:" + StickBounds.y);
                }*/
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                returnStick();
            }
        });
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    /*public void update(float x, float y, boolean isDownTouch, int pointer){
            if(CircleBounds.Overlaps(StickBounds) && isDownTouch && pointer == this.pointer){atControl(new Vector2(x,y)); }
            if((!isDownTouch && pointer == this.pointer)  ||(isDownTouch && pointer == this.pointer && !CircleBounds.isContains(touch))) returnStick();
        }
        */
    public void returnStick(){
        StickBounds.setPosition(CircleBounds.x, CircleBounds.y);
        direction.set(0,0);
        this.pointer = -1;
    }

    public void atControl (float x, float y) {
        StickBounds.setPosition(CircleBounds.x - Rcirle + x,CircleBounds.y - Rcirle +y);
        float dx =  StickBounds.x - CircleBounds.x;
        float dy = StickBounds.y - CircleBounds.y;
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

    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(CircleImg, CircleBounds.x - Rcirle, CircleBounds.y - Rcirle, Rcirle *2, Rcirle *2);
        batch.draw(StickImg, StickBounds.x - Rstick, StickBounds.y - Rstick, Rstick * 2 , Rstick * 2);
    }
}
