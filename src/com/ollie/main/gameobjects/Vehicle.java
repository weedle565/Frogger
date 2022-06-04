package com.ollie.main.gameobjects;

import com.ollie.main.Game;
import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Vehicle extends GameObject {

    BufferedImage[] sprite;

    private int vehicle;

    public Vehicle(int x, int y, Lane l) {
        super(x, y, l.direction(), new Rectangle(x, y, 16+10, 16+5));

        vehicle = new Random().nextInt(2);

        sprite = new BufferedImage[4];
        sprite[0] = SpriteHandler.getSprite(8, 0,16);
        sprite[1] = SpriteHandler.getSprite(9, 0, 16);
        sprite[2] = SpriteHandler.getSprite(10, 0, 17);
        sprite[3] = SpriteHandler.getSprite(11, 0, 17);

    }

    public void check(){
        if(super.getX() < 0 && super.getDirection() == 1){
            super.setX(522);
            vehicle = new Random().nextInt(2);
        } else if(super.getX() > 512 && super.getDirection() == 0){

            super.setX(-140);
            vehicle = new Random().nextInt(2);
        }

    }

    @Override
    public boolean checkCollisions(GameObject o) {
        if(o instanceof Player){
            if(o.getCollisionDetector().intersects(super.getCollisionDetector())){
                Game.setDead(true);
            }
        }
        return false;
    }

    @Override
    public void move(int x, int y) {
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);

        super.getCollisionDetector().x = super.getX();
        super.getCollisionDetector().y = super.getY();
    }

    @Override
    public void drawImage(Graphics g) {
        if(vehicle == 0 && super.getDirection() == 1){
            g.drawImage(sprite[0], super.getX(), super.getY(), sprite[0].getWidth()*2, sprite[0].getHeight()*2, null);
        } else if(vehicle == 1 && super.getDirection() == 1){
            g.drawImage(sprite[1], super.getX(), super.getY(), sprite[1].getWidth()*2, sprite[1].getHeight()*2, null);
        } else if(vehicle == 0 && super.getDirection() == 0){
            g.drawImage(sprite[2], super.getX(), super.getY(), sprite[2].getWidth()*2, sprite[2].getHeight()*2, null);
        } else if(vehicle == 1 && super.getDirection() == 0){
            g.drawImage(sprite[3], super.getX(), super.getY(), sprite[3].getWidth()*2, sprite[3].getHeight()*2, null);
        }
    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

    }
}
