package com.ollie.main.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteHandler{

    private static BufferedImage spriteSheet;


    public static BufferedImage loadImage(String dir) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("src/resources/" + dir + ".png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid, int TILE_SIZE){

        if(spriteSheet == null){
            spriteSheet = loadImage("spriteSheet");
        }

        return spriteSheet.getSubimage(xGrid*TILE_SIZE+1, yGrid*16, TILE_SIZE, 16);

    }

}
