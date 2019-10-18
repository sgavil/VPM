package com.gavilanvillar.android_tech;

import android.graphics.Bitmap;

import com.gavilanvillar.abs_layer.Image;

public class AImage implements Image {

    public AImage(Bitmap bitmap) {
        this._bitMap = bitmap;
    }

    @Override
    public int getWidth() {
        return _bitMap.getWidth();
    }

    @Override
    public int getHeight() {
        return _bitMap.getHeight();
    }

    private Bitmap _bitMap;
}
