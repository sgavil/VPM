package com.gavilanvillar.android_engine;

import android.graphics.Bitmap;

import com.gavilanvillar.engine.Image;

public class AImage implements Image {

    public AImage(Bitmap image) {
        this._image = image;
    }

    @Override
    public int getWidth() {
        return _image.getWidth();
    }

    @Override
    public int getHeight() {
        return _image.getHeight();
    }

    private Bitmap _image;
}
