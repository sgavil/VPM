package com.gavilanvillar.pc_tech;

import com.gavilanvillar.abs_layer.Image;

public class PCImage implements Image {

    public PCImage(java.awt.Image sprite){
        this._sprite = sprite;
    }

    @Override
    public int getWidth() {

        return 0;
    }

    @Override
    public int getHeight() {

        return 0;
    }

    private java.awt.Image _sprite;
}
