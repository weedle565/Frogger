package com.ollie.main.gameobjects;

import com.ollie.main.image.ImageLoaderIO;

import java.awt.*;

public abstract class GameObject implements ImageLoaderIO {

    private int x;
    private int y;
    private int direction;

    private final Rectangle collisionDetector;

    public GameObject(int x, int y, int direction, Rectangle collisionDetector){
        this.x = x;
        this.y = y;
        this.direction = direction;

        this.collisionDetector = collisionDetector;

    }

    public Rectangle getCollisionDetector() {
        return collisionDetector;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void drawCollisionDetector(Graphics g){

        g.setColor(Color.CYAN);
        g.drawRect(collisionDetector.x, collisionDetector.y, collisionDetector.width, collisionDetector.height);

    }

    public void check(){

    }

    public abstract boolean checkCollisions(GameObject o);

    public abstract void move(int x, int y);

    @Override
    public abstract void drawImage(Graphics g);

    @Override
    public abstract void animate(Graphics g);
}
