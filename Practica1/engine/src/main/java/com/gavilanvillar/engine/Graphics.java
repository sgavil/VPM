package com.gavilanvillar.engine;

/**
 * Clase Graphics
 *
 * Proporciona las funcionalidades básicas mínimas sobre la ventana
 * de la aplicación
 */
public interface Graphics {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos encargados de la gestión de las imágenes
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Carga una imagen almacenada en el contenedor de recursos de la aplicación a partir de
     * su nombre.
     *
     * @param name nombre del archivo
     * @return la imagen
     */
    Image newImage(String name);




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos encargados de pintar en pantalla
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Borra el contenido completo de la ventana, rellenándolo con un color recibido como parámetro.
     * @param color color con el que rellenar la ventana
     */
    void clear(int color);

    /**
     * Pinta la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param x Posición "x" en coordenadas del juego
     * @param y Posición "y" en coordenadas del juego
     */
    void drawImage(Sprite sprite, int x, int y, float alpha);

    /**
     * Pinta la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param dest Rectángulo destino de la pantalla donde se pintará la imagen
     */
    void drawImage(Sprite sprite, Rect dest, float alpha);

    /**
     * Pinta la imagen "image" en el centro de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     */
    void drawImageCentered(Sprite sprite, float alpha);

    /**
     * Pinta la imagen "image" en el centro del eje X de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param y Posición "y" en coordenadas del juego
     */
    void drawImageCenteredAxisX(Sprite sprite, int y, float alpha);

    /**
     * Pinta la imagen "image" en el centro del eje Y de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param sprite Objeto Sprite que contiene la imagen y los rectangulos fuente y destino
     * @param x Posición "x" en coordenadas del juego
     */
    void drawImageCenteredAxisY(Sprite sprite, int x, float alpha);

    /**
     * Método vacío que se llamará para realizar el pintado de la imagen. Las clases que
     * heredan de esta deben reimplementarlo.
     *
     * @param image Imagen a pintar
     * @param srcRect Rectángulo fuente de la imagen
     * @param destRect Rectángulo destino de la pantalla donde se pintará la imagen
     */
    void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha);




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //               Getters y Setters (de Graphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Devuelve el ancho de la ventana.
     *
     * @return ancho
     */
    int getWidth();

    /**
     * Devuelve el alto de la ventana.
     *
     * @return alto
     */
    int getHeight();

    /**
     * Devuelve la coordenada inicial X del juego
     *
     * @return x
     */
    int getInitialX();

    /**
     * Devuelve la coordenada inicial Y del juego
     *
     * @return y
     */
    int getInitialY();

    /**
     * Devuelve el factor de escala necesario para mantener la resolución
     *
     * @return factor de escala
     */
    float getScaleFactor();

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
    void setLogicResolution(int w, int h);

    /**
     * Método que guarda el tamaño de la físico de la pantalla. Por ejemplo, 2060x1080
     *
     * Además llama a los métodos que se encargan de calcular el nuevo factor de escala y
     * la nueva posición inicial.
     *
     * @param w Ancho de la pantalla
     * @param h Alto de la pantalla
     */
    void setPhysicResolution(int w, int h);

    /**
     * Método para modificar los valores del tamaño físico cuando cambia la orientación de la
     * pantalla. Intercambia los valores de alto y ancho de la pantalla respectivamente.
     */
    void swapPhysicResolution();

} // interface Graphics
