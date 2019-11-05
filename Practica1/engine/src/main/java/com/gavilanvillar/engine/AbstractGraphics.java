package com.gavilanvillar.engine;

/**
 * Clase AbstractGraphics
 *
 * Implementa la interfac Graphics. Contiene los métodos genéricos para el escalado.
 */
public abstract class AbstractGraphics implements Graphics {

    /**
     * Método que solo llama al principio de la aplicación para guardar los valores
     * de la resolución que se mantendrá durante la ejecución. Por ejemplo, 1920x1080.
     *
     * También llama a métodos que calculan el factor de escalado según la orientación de la
     * pantalla y calculan la posición inicial.
     *
     * @param w Ancho de la resolución
     * @param h Alto de la resolución
     */
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
    public void setPhysicResolution(int w, int h){

        _physicWidth = w;
        _physicHeight = h;

        setScaleFactor();
        setInitialPos();

    } // setPhysicResolution

    /**
     * Método que se llama cada vez que cambia la orientación de la pantalla. Intercambia
     * los valores de alto y ancho de la pantalla respectivamente.
     */
    public void swapPhysicResolution(){

        setPhysicResolution(_physicHeight, _physicWidth);

    } // swapPhysicsResolution

    public void drawImage(Image image, Rect src, int x, int y, float alpha){

        // Calculo de la posición inicial en coordenadas del juego donde se pintará la imagen
        int newX = _initialX + (int)(x * _scaleFactor);
        int newY = _initialY + (int)(y * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(image, src, destRect, alpha);

    } // drawImage

    public void drawImage(Image image, Rect src, Rect dest, float alpha){
        // Calculo de la posición inicial en coordenadas del juego donde se pintará la imagen
        int newX = _initialX + (int)(dest._left * _scaleFactor);
        int newY = _initialY + (int)(dest._top * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(dest._width * _scaleFactor) + newX,
                newY, (int)(dest._height * _scaleFactor) + newY);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(image, src, destRect, alpha);

    } // drawImage


    public void drawImageCentered(Image image, Rect src, float alpha){

        // Calculo de la posición inicial (centrada) en coordenadas del juego donde se pintará la imagen
        int newX = _midX - (int)((src._width * _scaleFactor) / 2);
        int newY = _midY - (int)((src._height * _scaleFactor) / 2);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(image, src, destRect, alpha);

    } // drawImageCentered

    public void drawImageCenteredAxisX(Image image, Rect src, int y, float alpha){

        // Calculo de la posición inicial (centrada en el eje X) en coordenadas del juego
        // donde se pintará la imagen
        int newX = _midX - (int)((src._width * _scaleFactor) / 2);
        int newY = _initialY + (int)(y * _scaleFactor);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(image, src, destRect, alpha);

    } // drawImageCenteredAxisX

    public void drawImageCenteredAxisY(Image image, Rect src, int x, float alpha){

        // Calculo de la posición inicial (centrada en el eje Y) en coordenadas del juego
        // donde se pintará la imagen
        int newX = _initialX + (int)(x * _scaleFactor);
        int newY = _midY - (int)((src._height * _scaleFactor) / 2);

        // Crea un nuevo rectángulo destino
        Rect destRect = new Rect(newX, (int)(src._width * _scaleFactor) + newX,
                newY, (int)(src._height * _scaleFactor) + newY);

        // Llama al "drawImagePrivate" con la imagen a pintar y los rectángulos fuente y destino
        drawImagePrivate(image, src, destRect, alpha);

    } // drawImageCenteredAxisY

    /**
     * Método vacío que se llamará para realizar el pintado de la imagen. Las clases que
     * heredan de esta deben reimplementarlo.
     *
     * @param image Imagen a pintar
     * @param srcRect Rectángulo fuente de la imagen
     * @param destRect Rectángulo destino de la pantalla donde se pintará la imagen
     */
    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha){

    } // drawImagePrivate

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

    @Override
    public int getWidth(){
        return _physicWidth;
    }

    @Override
    public int getHeight() {
        return _physicHeight;
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AbstractGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private int _physicWidth = 0;
    private int _physicHeight = 0;
    private int _logicWidth = 0;
    private int _logicHeight = 0;

    private int _midX = 0;
    private int _midY = 0;

    private float _scaleFactor = 0;
    private int _initialX = 0;
    private int _initialY = 0;

} // class AbstractGraphics
