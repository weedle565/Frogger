package com.ollie.main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){

        setSize(new Dimension(512, 512));
        setTitle("Frogger");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Game());
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(Main::new);

    }
}
