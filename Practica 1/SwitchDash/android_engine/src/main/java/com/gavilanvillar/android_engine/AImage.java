package com.gavilanvillar.android_engine;

import android.graphics.Bitmap;

import com.gavilanvillar.engine.Image;


/**
 * Clase AImage
 *
 * Clase que guarda la información del Bitmap perteneciente a una imagen
 */
public class AImage implements Image {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Constructora y métodos de inicialización (de AImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public AImage(Bitmap image) {

        this._image = image;

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //               Getters y Setters (de AImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Devuelve el Bitmap
     *
     * @return image
     */
    public Bitmap getImage() {

        return _image;

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos reimplementados (de Image)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public int getWidth() {

        return _image.getWidth();

    }

    @Override
    public int getHeight() {

        return _image.getHeight();

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AImage)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Bitmap _image;
}
