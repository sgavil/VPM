package com.gavilanvillar.pc_tech;

import com.gavilanvillar.abs_layer.Graphics;
import com.gavilanvillar.abs_layer.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PCGraphics implements Graphics {

    @Override
    public Image newImage(String name) {
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(new java.io.File(name));
        }
        catch(IOException e) {
            System.err.println("<JGrapichs> No se ha podido cargar el recurso: " + name);
        }

        return new PCImage(sprite);
    }


    @Override
    public void clear(int color) {

    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public int getWidht() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}

