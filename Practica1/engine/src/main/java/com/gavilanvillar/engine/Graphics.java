package com.gavilanvillar.engine;

/**
 * Clase Graphics
 *
 * Proporciona las funcionalidades básicas mínimas sobre la ventana
 * de la aplicación
 */
public interface Graphics {

    /**
     * Pone el canvas en las coordenadas del juego con el escalado.
     *
     * @param x Posición "x" en coordenadas del juego
     * @param y Posición "y" en coordenadas del juego
     */
    void setCanvasSize(int x, int y);

    public Image newImage(String name);

    void clear(int color);

    /**
     * Pinta la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param x Posición "x" en coordenadas del juego
     * @param y Posición "y" en coordenadas del juego
     */
    void drawImage(Image image, int x, int y);

    /**
     * Pinta la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param src Rectángulo fuente de la imagen
     * @param x Posición "x" en coordenadas del juego
     * @param y Posición "y" en coordenadas del juego
     */
    void drawImage(Image image, Rect src, int x, int y);

    /**
     * Pinta la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param src Rectángulo fuente de la imagen
     * @param dest Rectángulo destino de la pantalla donde se pintará la imagen
     */
    void drawImage(Image image, Rect src, Rect dest);

    /**
     * Pinta la imagen "image" en el centro de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param src Rectángulo fuente de la imagen
     */
    void drawImageCentered(Image image, Rect src);

    /**
     * Pinta la imagen "image" en el centro del eje X de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param src Rectángulo fuente de la imagen
     * @param y Posición "y" en coordenadas del juego
     */
    void drawImageCenteredAxisX(Image image, Rect src, int y);

    /**
     * Pinta la imagen "image" en el centro del eje Y de la pantalla/ventana.
     * Mapea cada pixel de la imagen en el pixel de la pantalla.
     *
     * @param image Imagen a pintar
     * @param src Rectángulo fuente de la imagen
     * @param x Posición "x" en coordenadas del juego
     */
    void drawImageCenteredAxisY(Image image, Rect src, int x);
    //void drawImage(Image image, Rect src, int x, int y);

    int getWidth();

    int getHeight();

}
