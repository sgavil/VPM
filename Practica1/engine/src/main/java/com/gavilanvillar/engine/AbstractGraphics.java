package com.gavilanvillar.engine;

/**
 * Clase que contiene los métodos genéricos para el escalado.
 */
public abstract class AbstractGraphics implements Graphics {

    public void setLogicResolution(int w, int h){
        this._logicWidth = w;
        this._logicHeight = h;
    }

    public void drawImage(Image image, int x, int y){
        // x e y están en coordenadas "lógicas de canvas/juego



        /*
        * int xFisico = ...;
        * int yFisico = ...;
        *
        * drawImagePrivate(...); se implementa en las clases de abajo
        * */
    }

    public void drawImagePrivate(Image image, int x, int y){

    }

    int _logicWidth = 0;
    int _logicHeight = 0;
}
