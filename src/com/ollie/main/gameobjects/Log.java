package com.ollie.main.gameobjects;

import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Log extends GameObject {

    private final BufferedImage sprite;

    public Log(int x, int y, Lane l) {
        super(x, y, l.direction(), new Rectangle(x-15, y, 80, 16));

        sprite = SpriteHandler.getSprite(1, 1, 45);
    }

    public void check(){
        if(super.getX() > 512 && super.getDirection() == 0){

            super.setX(-140);
        }
    }

    @Override
    public boolean checkCollisions(GameObject o) {
        return false;
    }

    @Override
    public void move(int x, int y) {
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);

        super.getCollisionDetector().x = super.getX();
        super.getCollisionDetector().y = super.getY()+5;
    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(sprite, super.getX(), super.getY(), sprite.getWidth()*2, sprite.getHeight()*2, null);

    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

    }
}
