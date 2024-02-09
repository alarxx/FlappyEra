package com.retro.androidgames.framework.math;

public class Circle {
    public final Vector2 center = new Vector2();
    public float radius;

    public Circle(float x, float y, float radius){
        this.center.set(x, y);
        this.radius = radius;
    }

    /*public float distSquared(Vector2 other) {
        float distX = this.center.x - other.x;
        float distY = this.center.y - other.y;
        return distX*distX + distY*distY;
    }

    public boolean overlapCircles(Circle c1, Circle c2) {
        float distance = c1.center.distSquared(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }*/
}
