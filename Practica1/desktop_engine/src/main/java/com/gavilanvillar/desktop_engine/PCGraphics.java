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

/**
 * Clase PCGraphics
 *
 * Hereda de AbstractGraphics
 */
public class PCGraphics extends AbstractGraphics {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Constructora y métodos de inicialización (de PCGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public PCGraphics(int windowWidth, int windowHeight){

        setPhysicResolution(windowWidth, windowHeight);

    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos reimplementados (de Graphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Crea una nueva imagen de Java y la almacena en la clase PCImage
    @Override
    public Image newImage(String name) {

        java.awt.Image image = null;

        try{

            // Crea una imagen de Java leyendo el archivo
            image = ImageIO.read(new java.io.File(name));

        }
        catch(IOException e) {

            System.err.println("<PCGrapichs> No se ha podido cargar el recurso: " + name);

        }

        // Devuelve una nueva PCImage
        return new PCImage(image);

    } // newImage

    @Override
    public void clear(int color) {

        // Configura el color actual con el que se pintará
        _graphics.setColor(new Color(color));

        // Crea un rectángulo del físico de la pantalla con el color configurado
        _graphics.fillRect(0, 0, getWidth(), getHeight());

    } // clear

    @Override
    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha) {

        // Se comprueba que existe el objeto "image" para evitar fallos
        if(image != null) {

            // Crea una variable Graphics2D que se usará para pintar la imagen con un "filtro"
            Graphics2D g2d = (Graphics2D)_graphics;

            // Se le añade el "filtro" de transparencia
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Se pinta la imagen usando la imagen de Java, y los rectángulos fuente y destino,
            // con el Graphics2D que tiene almacenado el "filtro" de transparencia
            g2d.drawImage(((PCImage) image).getAWTImage(),
                    destRect._left, destRect._top,
                    destRect._right, destRect._bottom,
                    srcRect._left, srcRect._top,
                    srcRect._right, srcRect._bottom,
                    null);

        }

    } // drawImagePrivate




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //              Getters y Setters (de PCGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void setGraphics(java.awt.Graphics g){

        this._graphics = g;

    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de PCGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private java.awt.Graphics _graphics;

}

