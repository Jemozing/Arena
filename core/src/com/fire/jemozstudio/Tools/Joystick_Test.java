/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Joystick_Test {
    /*TextureRegion CircleImg, StickImg;
    Circle CircleBounds, StickBounds;
    float Rcirle, Rstick;
    private int pointer = -1;

    Point2D direction;

    public Joystick_Test(TextureRegion cimg, TextureRegion simg, Point2D point, float Size){
        CircleImg = cimg;
        StickImg = simg;
        Rcirle = Size / 2;
        Rstick = Rcirle / 2;
        CircleBounds = new Circle(point, Rcirle);
        StickBounds = new Circle(point, Rstick);
        direction = new Point2D(0,0);
    }

    public void draw(SpriteBatch batch){
        batch.draw(CircleImg, CircleBounds.pos.getX() - Rcirle, CircleBounds.pos.getY() - Rcirle, Rcirle *2, Rcirle *2);
        batch.draw(StickImg, StickBounds.pos.getX() - Rstick, StickBounds.pos.getY() - Rstick, Rstick * 2 , Rstick * 2);
    }

    public void update(float x, float y, boolean isDownTouch, int pointer){
        Point2D touch = new Point2D(x,y);
        if(CircleBounds.isContains(touch) && isDownTouch && this.pointer == -1) this.pointer = pointer;
        if(/*CircleBounds.Overlaps(StickBounds) &&*/ //isDownTouch && pointer == this.pointer)atControl(new Point2D(x,y));
     /*   if((!isDownTouch && pointer == this.pointer)  /*||(isDownTouch && pointer == this.pointer && !CircleBounds.isContains(touch))*///) returnStick();
 /*   }

    public void returnStick(){
        StickBounds.pos.setPoint(CircleBounds.pos);
        direction.setPoint(0,0);
        this.pointer = -1;
    }

    public Point2D getDirection() {
        return direction;
    }

    public void atControl (Point2D point) {
            StickBounds.pos.setPoint(point);
            float dx = CircleBounds.pos.getX() - StickBounds.pos.getX();
            float dy = CircleBounds.pos.getY() - StickBounds.pos.getY();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            direction.setPoint(-(dx/dist), -(dy/dist));
    }
    */
}
