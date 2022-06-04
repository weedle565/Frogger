package com.ollie.main.gameobjects;

import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Turtle extends GameObject {

    BufferedImage[] sprite;

    private int stage;

    public Turtle(int x, int y, Lane l) {
        super(x, y, l.direction(), new Rectangle(x+20, y, 16, 16));

        sprite = new BufferedImage[4];

        stage = 0;

        sprite[0] = SpriteHandler.getSprite(13, 0, 16);
        sprite[1] = SpriteHandler.getSprite(14, 0, 16);
        sprite[2] = SpriteHandler.getSprite(0, 1, 15);
        sprite[3] = sprite[1];
    }

    public void check(){
        if(super.getX() < 0 && getDirection() == 1){
            super.setX(522);
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

        super.getCollisionDetector().x = super.getX()+15;
        super.getCollisionDetector().y = super.getY()+5;
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(sprite[stage], super.getX(), super.getY(), sprite[0].getWidth()*2, sprite[0].getHeight()*2, null);
    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);


        if(stage == 3){
            stage = 0;
            return;
        }

        stage++;
    }
}
