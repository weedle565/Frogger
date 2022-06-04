package com.ollie.main.gameobjects;

import com.ollie.main.image.SpriteHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    BufferedImage[] sprite;
    BufferedImage deadAnimation;
    private int stage;

    public Player(int x, int y) {
        super(x, y, 0, new Rectangle(x+15, y, 16, 16));

        stage = 0;

        sprite = new BufferedImage[8];

        deadAnimation = SpriteHandler.getSprite(1,1, 16);

        for(int i = 0; i < 8; i++){

            sprite[i] = SpriteHandler.getSprite(i, 0, 15);

        }

    }

    public void drawDead(Graphics g){
        g.drawImage(deadAnimation, super.getX(), super.getY(), sprite[stage].getWidth()*2, sprite[stage].getHeight()*2, null);

    }

    public boolean checkCollisions(GameObject o) {
        if(o instanceof Log){
            return checkLogCollisions((Log)o) ;
        }

        if(o instanceof Turtle){

            return checkTurtleCollisions((Turtle) o);

        }

        return true;
    }

    private boolean checkLogCollisions(Log l){
        if(l.getY() == super.getY()){
            if((l.getCollisionDetector().intersects(super.getCollisionDetector()))){

                System.out.println("alive");
                return true;

            } else {

                System.out.println("dead");
                return false;
            }
        }

        return true;
    }

    private boolean checkTurtleCollisions(Turtle t){
        if(t.getY() == super.getY()){
            if((t.getCollisionDetector().intersects(super.getCollisionDetector()))){
                super.setX(t.getX());
                return true;

            } else {

                return false;
            }
        }

        return true;
    }

    public boolean checkWon(Lane l){

        return l.y() == super.getY();
    }

    @Override
    public void move(int x, int y) {
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);
    }

    @Override
    public void drawImage(Graphics g) {

        g.drawImage(sprite[super.getDirection() + stage], super.getX(), super.getY(), sprite[stage].getWidth()*2, sprite[stage].getHeight()*2, null);

        super.getCollisionDetector().x = super.getX()+5;
        super.getCollisionDetector().y = super.getY()+5;
    }

    @Override
    public void animate(Graphics g) {

        drawImage(g);

        if(stage == 1){
            stage = 0;
            return;
        }

        stage = 1;
    }

}
