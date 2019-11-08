package com.gavilanvillar.engine;

/**
 * Clase AbstractGraphics
 *
 * Implementa la interfac Graphics. Contiene los métodos genéricos para el escalado.
 */
public abstract class AbstractGraphics implements Graphics {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //            Métodos reimplementados (de Graphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public void drawImage(Sprite sprite, int x, int y, float alpha){

        Rect src = sprite.getSrcRect();

        // Calculo de la posición inicial en coordenadas del juego donde se pintará la imagen
        int newX = _initialX + (int)(x * _scaleFactor);
        int newY = _initialY + (int)(y * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        sprite.setDestRect(destRect);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(sprite.getImage(), src, destRect, alpha);

    } // drawImage

    @Override
    public void drawImage(Sprite sprite, Rect dest, float alpha){

        Rect src = sprite.getSrcRect();

        // Calculo de la posición inicial en coordenadas del juego donde se pintará la imagen
        int newX = _initialX + (int)(dest._left * _scaleFactor);
        int newY = _initialY + (int)(dest._top * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(dest._width * _scaleFactor) + newX,
                newY, (int)(dest._height * _scaleFactor) + newY);

        sprite.setDestRect(destRect);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(sprite.getImage(), src, destRect, alpha);

    } // drawImage

    @Override
    public void drawImageCentered(Sprite sprite, float alpha){

        Rect src = sprite.getSrcRect();

        // Calculo de la posición inicial (centrada) en coordenadas del juego donde se pintará la imagen
        int newX = _midX - (int)((src._width * _scaleFactor) / 2);
        int newY = _midY - (int)((src._height * _scaleFactor) / 2);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        sprite.setDestRect(destRect);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(sprite.getImage(), src, destRect, alpha);

    } // drawImageCentered

    @Override
    public void drawImageCenteredAxisX(Sprite sprite, int y, float alpha){

        Rect src = sprite.getSrcRect();

        // Calculo de la posición inicial (centrada en el eje X) en coordenadas del juego
        // donde se pintará la imagen
        int newX = _midX - (int)((src._width * _scaleFactor) / 2);
        int newY = _initialY + (int)(y * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        sprite.setDestRect(destRect);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(sprite.getImage(), src, destRect, alpha);

    } // drawImageCenteredAxisX

    @Override
    public void drawImageCenteredAxisY(Sprite sprite, int x, float alpha){

        Rect src = sprite.getSrcRect();

        // Calculo de la posición inicial (centrada en el eje Y) en coordenadas del juego
        // donde se pintará la imagen
        int newX = _initialX + (int)(x * _scaleFactor);
        int newY = _midY - (int)((src._height * _scaleFactor) / 2);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        sprite.setDestRect(destRect);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(sprite.getImage(), src, destRect, alpha);

    } // drawImageCenteredAxisY

    @Override
    public int getWidth(){

        return _physicWidth;

    } // getWidth

    @Override
    public int getHeight() {

        return _physicHeight;

    } // getHeight

    @Override
    public int getInitialX(){

        return _initialX;

    } // getInitialX

    @Override
    public int getInitialY(){

        return _initialY;

    } // getInitialY

    @Override
    public float getScaleFactor(){

        return _scaleFactor;

    } // getScaleFactor

    @Override
    public void setLogicResolution(int w, int h){

        _logicWidth = w;
        _logicHeight = h;

        setScaleFactor();
        setInitialPos();

    } // setLogicResolution

    @Override
    public void setPhysicResolution(int w, int h){

        _physicWidth = w;
        _physicHeight = h;

        setScaleFactor();
        setInitialPos();

    } // setPhysicResolution

    @Override
    public void swapPhysicResolution(){

        setPhysicResolution(_physicHeight, _physicWidth);

    } // swapPhysicsResolution




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos protegidos/privados (de AbstractGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método que calcula el factor de escalado de la ventana lógica del juego según
     * la ventana física y la resolución esperada.
     */
    private void setScaleFactor(){

        // Inicializa dos "floats" al valor máxim o para después elegir el menor de ambos
        float scaleW = Float.MAX_VALUE;
        float scaleH = Float.MAX_VALUE;

        // Calcula los factores de escala que harían falta para ajustar el ancho y el alto
        // a la ventana
        scaleW = (float)_physicWidth / (float)_logicWidth;
        scaleH = (float)_physicHeight / (float)_logicHeight;

        // Elige el menor factor de escalado debido a que es el lado que ha habido que ajustar
        // más a la ventana
        _scaleFactor = Math.min(scaleH, scaleW);

    } // setScaleFactor

    /**
     * Método que calcula la posición inicial (0, 0) en coordenadas del juego dentro de la
     * ventana física. Además calcula el centro en los ejes X e Y de la ventana.
     */
    private void setInitialPos(){

        // Calcula la posición inicial (0, 0) según el factor de escalado actual
        _initialX = (_physicWidth - (int)(_logicWidth * _scaleFactor)) / 2;
        _initialY = (_physicHeight - (int)(_logicHeight * _scaleFactor)) / 2;

        // Calcula el centro de ambos ejes
        _midX = (int)(_physicWidth / 2);
        _midY = (int)(_physicHeight / 2);

    } // setInitialPos




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //      Atributos protegidos/privados (de AbstractGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Tamaño físico de la pantalla
    private int _physicWidth = 0;
    private int _physicHeight = 0;

    // Resolución
    private int _logicWidth = 0;
    private int _logicHeight = 0;

    // Centro de la pantalla en los ejes X e Y
    private int _midX = 0;
    private int _midY = 0;

    // Factor de escalado
    private float _scaleFactor = 0;

    // Posición (x, y) inicial de las coordenadas del juego
    private int _initialX = 0;
    private int _initialY = 0;

} // class AbstractGraphics
