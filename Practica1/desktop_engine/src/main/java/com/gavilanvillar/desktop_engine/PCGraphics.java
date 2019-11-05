package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Rect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PCGraphics extends AbstractGraphics {

    public PCGraphics(int windowWidth, int windowHeight){
        super.setPhysicResolution(windowWidth, windowHeight);
    }

    public void setGraphics(java.awt.Graphics g){
        this._graphics = g;
    }

    @Override
    public void setCanvasSize(int x, int y){

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
        _graphics.setColor(Color.black);
        _graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha) {
        if(image != null) {
            Graphics2D g2d = (Graphics2D)_graphics.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(((PCImage) image).getAWTImage(),
                    destRect._left, destRect._top,
                    destRect._right, destRect._bottom,
                    srcRect._left, srcRect._top,
                    srcRect._right, srcRect._bottom,
                    null);
            //g2d.dispose();
            //System.out.print(destRect._width);
        }
    }

    java.awt.Graphics _graphics;

}

