package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Image;

public class PCImage implements Image {

    public PCImage(java.awt.Image _image){
        this._image = _image;
    }

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

    private java.awt.Image _image;
}
