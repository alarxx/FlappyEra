package com.retro.androidgames.framework.math;

public class Rectangle {
    public final Vector2 lowerLeft;
    public float width, height;

    public Rectangle(float x, float y, float width, float height){
        this.lowerLeft = new Vector2(x - width/2, y-height/2);
        this.width = width;
        this.height = height;
    }
}
