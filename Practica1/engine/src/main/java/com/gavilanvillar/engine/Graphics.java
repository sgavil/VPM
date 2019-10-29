package com.gavilanvillar.engine;

public interface Graphics {

    /**
     * Pone el canvas en las coordenadas del juego con el escalado.
     *
     * @param x posición x en coordenadas del juego
     * @param y posición y en coordenadas del juego
     */
    void setCanvasSize(int x, int y);

    public Image newImage(String name);

    void clear(int color);

    /**
     * Dibuja completamente la imagen "image" en la posición (x, y) de la pantalla/ventana.
     * Mapea cada pixel de la imagen en mi pixel de pantalla.
     *
     * @param image Imagen a pintar
     * @param x Posición x en la pantalla/ventana
     * @param y Posición y en la pantalla/ventana
     */
    void drawImage(Image image, int x, int y);

    //void drawImage(Image image, Rect src, int x, int y);

    //void drawImage(Image image, Rect src, Rect dest);

    //void drawImage(Image image, Rect src, Rect dest, int alpha);

    int getWidth();

    int getHeight();

}
