package com.gavilanvillar.engine;

/**
 * Clase Rect
 *
 * Contiene los parámetros de tamaño de un SPRITE.
 */
public class Rect {

    /**
     * @param _left Posicion izquierda del rectángulo
     * @param _right Posicion derecha del rectángulo
     * @param _top Posición superior del rectángulo
     * @param _bottom Posición inferior del rectángulo
     */
    public Rect(int _left, int _right, int _top, int _bottom) {
        this._left = _left;
        this._right = _right;
        this._width = this._right - this._left;

        this._top = _top;
        this._bottom = _bottom;
        this._height = this._bottom - this._top;
    } // Rect

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos públicos (de Rect)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public int _left;
    public int _right;
    public int _width;

    public int _top;
    public int _bottom;
    public int _height;

} // class Rect
