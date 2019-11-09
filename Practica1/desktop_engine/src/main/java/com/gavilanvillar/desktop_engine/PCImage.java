package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Image;

/**
 * Clase PCImage
 *
 * Clase que guarda la información perteneciente a una imagen
 */
public class PCImage implements Image {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Constructora y métodos de inicialización (de PCImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public PCImage(java.awt.Image _image){
        this._image = _image;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //               Getters y Setters (de PCImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public java.awt.Image getAWTImage(){
        return _image;
    }

    @Override
    public int getWidth() {

       return _image.getWidth(null);
    }


    @Override
    public int getHeight() {

        return _image.getHeight(null);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de PCImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private java.awt.Image _image;
}
