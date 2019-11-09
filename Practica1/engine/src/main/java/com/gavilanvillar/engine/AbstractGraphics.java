package com.gavilanvillar.engine;

/**
 * Clase AbstractGraphics
 *
 * Implementa la interfaz Graphics. Contiene los métodos genéricos para el escalado.
 */
public abstract class AbstractGraphics implements Graphics {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //            Métodos reimplementados (de Graphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Dibuja una imagen en x,y con una transparencia alpha
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param x Posición "x" en coordenadas del juego
     * @param y Posición "y" en coordenadas del juego
     * @param alpha valor de la transparencia de la imagen
     */
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

    /**
     * Dibuja una imagen en las posiciones dadas en el rectangulo dest con transparencia
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param dest Rectángulo destino de la pantalla donde se pintará la imagen
     * @param alpha valor que toma la transparencia de la imagen
     */
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

    /**
     * Dibuja una imagen centrada en la pantalla
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param alpha Valor de la transparencia de la imagen
     */
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

    /**
     * Dibuja una imagen centrada en el eje X de la ventana
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param y Posición "y" en coordenadas del juego
     * @param alpha Valor de la transparencia de la imagen
     */
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

    /**
     * Dibuja una imagen centrada en el eje Y de la ventana
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param x Posición "x" en coordenadas del juego
     * @param alpha Valor de la transparencia de la imagen
     */
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

    /**
     * @return Ancho fisico del dispositivo
     */
    @Override
    public int getWidth(){

        return _physicWidth;

    } // getWidth

    /**
     * @return Alto fisico del dispositivo
     */
    @Override
    public int getHeight() {

        return _physicHeight;

    } // getHeight

    /**
     * @return Coordenada inicial X del juego
     */
    @Override
    public int getInitialX(){

        return _initialX;

    } // getInitialX

    /**
     * @return Coordenada inicial Y del juego
     */
    @Override
    public int getInitialY(){

        return _initialY;

    } // getInitialY

    /**
     * Devuelve el factor de escala necesario para mantener la resolución
     *
     * @return factor de escala
     */
    @Override
    public float getScaleFactor(){

        return _scaleFactor;

    } // getScaleFactor

    /**
     * Método que guarda los valores de la resolución que se mantendrá durante la ejecución.
     * Por ejemplo, 1920x1080.
     *
     * Llama a métodos que calculan el factor de escalado según la orientación de la
     * pantalla y calculan la posición inicial.
     *
     * @param w Ancho de la resolución
     * @param h Alto de la resolución
     */
    @Override
    public void setLogicResolution(int w, int h){

        _logicWidth = w;
        _logicHeight = h;

        setScaleFactor();
        setInitialPos();

    } // setLogicResolution

    /**
     * Método que guarda el tamaño de la físico de la pantalla. Por ejemplo, 2060x1080
     *
     * Además llama a los métodos que se encargan de calcular el nuevo factor de escala y
     * la nueva posición inicial.
     *
     * @param w Ancho de la pantalla
     * @param h Alto de la pantalla
     */
    @Override
    public void setPhysicResolution(int w, int h){

        _physicWidth = w;
        _physicHeight = h;

        setScaleFactor();
        setInitialPos();

    } // setPhysicResolution

    /**
     * Método para modificar los valores del tamaño físico cuando cambia la orientación de la
     * pantalla. Intercambia los valores de alto y ancho de la pantalla respectivamente.
     */
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
