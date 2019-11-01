package com.gavilanvillar.engine;

public class Sprite {

    public Sprite(Image image, Rect rect){
        this._image = image;
        this._rect = rect;
    }

    public void draw(Graphics g, int x, int y){
        g.drawImage(_image, x, y);
    }

    public void draw(Graphics g, Rect src, Rect dest){
        g.drawImage(_image, src, dest);
    }

    public void drawCentered(){

    }

    public Image getImage(){
        return _image;
    }

    public Rect getRect(){
        return _rect;
    }

    private Image _image;

    private Rect _rect;

} // interface Sprite
