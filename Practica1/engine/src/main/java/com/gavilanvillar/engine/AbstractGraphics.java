package com.gavilanvillar.engine;

/**
 * Clase que contiene los métodos genéricos para el escalado.
 */
public abstract class AbstractGraphics implements Graphics {

    public void setLogicResolution(int w, int h){
        _logicWidth = w;
        _logicHeight = h;

    }

    public void setPhysicResolution(int w, int h){
        _physicWidth = w;
        _physicHeight = h;
    }

    public void swapPhysicResolution(){
        setPhysicResolution(_physicHeight, _physicWidth);
    }

    public void drawImage(Image image, int x, int y){
        // x e y están en coordenadas "lógicas de canvas/juego

        setScaleFactor();
        setInitialPos();

        Rect srcRect = new Rect(0, image.getWidth(), 0, image.getHeight());
        int newX = _initialX + x;
        int newY = _initialY + y;
        Rect destRect = new Rect(newX, (int)(image.getWidth() * _scaleFactor) + newX,
                newY, (int)(image.getHeight() * _scaleFactor) + newY);

        drawImagePrivate(image, srcRect, destRect);
    }

    public void drawImage(Image image, Rect src, Rect dest){
        setScaleFactor();
        setInitialPos();

        int newX = _initialX + dest._left;
        int newY = _initialY + dest._top;
        Rect destRect = new Rect(newX, (int)(dest._right * _scaleFactor) + newX,
                newY, (int)(dest._bottom * _scaleFactor) + newY);

        drawImagePrivate(image, src, destRect);
    }

    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect){

    }

    private void setScaleFactor(){
        float scaleW = Float.MAX_VALUE;
        float scaleH = Float.MAX_VALUE;
        scaleW = (float)_physicWidth / (float)_logicWidth;
        scaleH = (float)_physicHeight / (float)_logicHeight;
        _scaleFactor = Math.min(scaleH, scaleW);
    }

    private void setInitialPos(){
        _initialX = (_physicWidth - (int)(_logicWidth * _scaleFactor)) / 2;
        _initialY = (_physicHeight - (int)(_logicHeight * _scaleFactor)) / 2;
    }

    @Override
    public int getWidth(){
        return _physicWidth;
    }

    @Override
    public int getHeight() {
        return _physicHeight;
    }

    int _physicWidth = 0;
    int _physicHeight = 0;
    int _logicWidth = 0;
    int _logicHeight = 0;

    float _scaleFactor = 0;
    int _initialX = 0;
    int _initialY = 0;
}
