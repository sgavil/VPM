package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;

import java.io.IOException;

import javax.imageio.ImageIO;


public class PCGraphics implements Graphics {

    public void setGraphics(java.awt.Graphics g){
        this._graphics = g;
    }

    @Override
    public Image newImage(String name) {
        java.awt.Image image = null;
        try{
            image = ImageIO.read(new java.io.File(name));
        }
        catch(IOException e) {
            System.err.println("<PCGrapichs> No se ha podido cargar el recurso: " + name);
        }

        return new PCImage(image);
    }


    @Override
    public void clear(int color) {

    }

    @Override
    public void drawImage(Image image, int x, int y) {
        _graphics.drawImage(((PCImage)image).getAWTImage(), x, y, null);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    java.awt.Graphics _graphics;
}

