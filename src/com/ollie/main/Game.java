package com.ollie.main;

import com.ollie.main.gameobjects.*;
import com.ollie.main.image.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game extends JPanel implements ActionListener {

    private Player p;
    private Background bg;

    private static ArrayList<GameObject> vehicles;
    private static ArrayList<Lane> lanes;

    private static boolean dead;
    private boolean won;

    public Game(){

        initGame();

    }


    private void initGame(){

        setFocusable(true);


        lanes = new ArrayList<>();
        vehicles = new ArrayList<>();

        addKeyListener(new KeyAdapter());

        dead = false;
        won = false;

        p = new Player(227, 435);

        Timer gameTimer = new Timer(100, this);
        Timer animationTimer = new Timer(300, (e) -> {

            if(!dead) {
                p.animate(this.getGraphics());

                vehicles.iterator().forEachRemaining(v -> v.animate(this.getGraphics()));
            }
        });

        initLanes();
        initVehicles();

        bg = new Background();

        animationTimer.start();
        gameTimer.start();

    }

    private void initLanes(){

        Random r = new Random();

        //Starting lanes
        lanes.add(new Lane(440, 0, 0, "start"));
        lanes.add(new Lane(405, 0, 0, "empty"));
        //Road lanes
        lanes.add(new Lane(370, r.nextInt(3,5), r.nextInt(0,2), "road"));
        lanes.add(new Lane(335, r.nextInt(2,6), r.nextInt(0,2), "road"));
        lanes.add(new Lane(295, r.nextInt(4,8), r.nextInt(0,2), "road"));
        lanes.add(new Lane(260, r.nextInt(1,4), r.nextInt(0,2), "road"));
        //Then the free lane
        lanes.add(new Lane(225, 0, 0, "empty"));
        //Water lanes
        lanes.add(new Lane(190, r.nextInt(2,5), r.nextInt(0,2), "water"));
        lanes.add(new Lane(155, r.nextInt(2,5), r.nextInt(0,2), "water"));
        lanes.add(new Lane(120, r.nextInt(3,5), r.nextInt(0,2), "water"));
        lanes.add(new Lane(85, r.nextInt(3,5), r.nextInt(0,2), "water"));
        lanes.add(new Lane(55, r.nextInt(1,5), r.nextInt(0,2), "water"));
        lanes.add(new Lane(25, r.nextInt(1,5), r.nextInt(0,2), "water"));
        //Final lane
        lanes.add(new Lane(0, 0, 0, "finish"));

    }

    private ArrayList<Lane> getLanes() {
        return lanes;
    }

    private void initVehicles() {

        for(Lane l : lanes){

            for(int i = l.vehicleNum(); i > 0; i--){

                if(l.direction() == 1 && l.type().equals("road")){
                    Vehicle vehicle = new Vehicle(512 + (i*70), l.y(), l);

                    vehicles.add(vehicle);

                    System.out.println(vehicle.getX());

                } else if(l.direction() == 0 && l.type().equals("road")){
                    Vehicle vehicle = new Vehicle(2 * -(i * 70), l.y(), l);

                    vehicles.add(vehicle);

                }else if(l.direction() == 1 && l.type().equals("water")){
                    Turtle turt = new Turtle(512+(i*70), l.y(), l);

                    vehicles.add(turt);

                } else if(l.direction() == 0 && l.type().equals("water")){
                    Log log = new Log(2 * -(i * 70), l.y(), l);

                    vehicles.add(log);

                }

            }

        }

    }

    @Override
    public void paintComponent(Graphics g){
        AtomicBoolean onLog = new AtomicBoolean(false);
        AtomicBoolean check = new AtomicBoolean(false);

        if(!dead) {
            bg.drawImage(g);

            p.drawImage(g);

            if(p.checkWon(lanes.get(lanes.size()-1))){
                won = true;
                dead = true;
            }

            objectIterator(g, onLog, check);

            p.drawImage(g);
            System.out.println(onLog.get());
            if(!onLog.get() && check.get()){
                dead = true;
            }

        } else if(won){

            bg.drawImage(g);
            p.drawImage(g);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Win!", 225, 225);
        } else {

            bg.drawImage(g);
            p.drawDead(g);

        }
    }

    private void objectIterator(Graphics g, AtomicBoolean onLog, AtomicBoolean check){

        vehicles.iterator().forEachRemaining(v -> {

            v.drawImage(g);
            if (v.getDirection() == 0) {
                v.move(5, 0);
            } else {
                v.move(-5, 0);
            }

            v.check();

            v.drawCollisionDetector(g);

            v.checkCollisions(p);

            if(p.getY() == v.getY() && (v instanceof Log || v instanceof Turtle)){
                check.set(true);
            }

            if(p.getY() == v.getY() && (v instanceof Log || v instanceof Turtle) && p.checkCollisions(v)){
                onLog.set(true);
                System.out.println(onLog.get());
            }

        });
    }

    public static void setDead(boolean dead) {
        Game.dead = dead;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    private class KeyAdapter extends java.awt.event.KeyAdapter {

        int row;

        private KeyAdapter(){
             row = 0;
        }

        @Override
        public void keyReleased(KeyEvent e){

            System.out.println(getLanes().size() + " " + row);

            if(!dead && !won) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_W -> {
                        if(!(row + 1 > 13)) {
                            row++;
                            p.setDirection(0);
                            p.setY(getLanes().get(row).y());
                        }

                    }
                    case KeyEvent.VK_S -> {
                        if(!(row - 1 < 0)) {
                            row--;
                            p.setDirection(4);
                            p.setY(getLanes().get(row).y());
                        }
                    }
                    case KeyEvent.VK_A -> {
                        p.setDirection(2);
                        p.move(-37, 0);
                    }
                    case KeyEvent.VK_D -> {
                        p.setDirection(6);
                        p.move(37, 0);
                    }

                }
            }

        }

    }
}
