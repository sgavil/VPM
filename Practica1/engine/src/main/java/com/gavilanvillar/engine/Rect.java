package com.gavilanvillar.engine;

public class Rect {
    public Rect(){}

    public Rect(int _left, int _width, int _top, int _height) {
        this._left = _left;
        this._width = _left + _width;
        this._height = _height;
        this._top = _top;
    }

    public int _left;
    public int _width;
    public int _height;
    public int _top;

}
