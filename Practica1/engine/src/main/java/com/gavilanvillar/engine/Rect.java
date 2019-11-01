package com.gavilanvillar.engine;

public class Rect {
    public Rect(){}

    public Rect(int _left, int _right, int _top, int _bottom) {
        this._left = _left;
        this._right = _right;
        this._width = this._right - this._left;

        this._top = _top;
        this._bottom = _bottom;
        this._height = this._bottom - this._top;
    }

    public int _left;
    public int _right;
    public int _width;

    public int _top;
    public int _bottom;
    public int _height;

}
