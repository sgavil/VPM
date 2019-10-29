package com.gavilanvillar.engine;

public class Sprite {

    public Sprite(Image image, Rect rect){
        this._image = image;
        this._rect = rect;
    }

    public void draw(Graphics g/*, ...*/){
        //g.drawImage(_image/*, ...*/);
    }

    public void drawCentered(){

    }

    private Image _image;

    private Rect _rect;

} // interface Sprite
