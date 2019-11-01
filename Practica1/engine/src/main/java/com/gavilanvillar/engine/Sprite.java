package com.gavilanvillar.engine;

/**
 * Clase Sprite
 *
 * Contiene la información sobre "Image" y "Rect" de una imagen, y los métodos básicos para llamar
 * a los métodos que realizan el pintado de la clase Graphics.
 *
 */
public class Sprite {

    public Sprite(Image image, Rect rect){
        this._image = image;
        this._rect = rect;
    }

    /**
     * Método para llamar a "drawImage" de Graphics
     *
     * @param g Graphics
     * @param x Posición "x" de la pantalla/ventana
     * @param y Posición "y" de la pantalla/ventana
     */
    public void draw(Graphics g, int x, int y){

        g.drawImage(_image, x, y);

    }

    /**
     * Método para llamar a "drawImage" de Graphics
     *
     * @param g Graphics
     * @param dest Rectángulo destino de la pantalla donde se pintará la imagen
     */
    public void draw(Graphics g, Rect dest){

        g.drawImage(_image, _rect, dest);

    }

    /**
     * Método para llamar a "drawImageCentered" de Graphics
     *
     * @param g Graphics
     * @param src Rectángulo fuente de la imagen
     */
    public void drawCentered(Graphics g, Rect src){

        g.drawImageCentered(_image, src);

    }

    /**
     * Método para pintar centrado en alguno de los dos ejes, X o Y
     *
     * @param g Graphics
     * @param src Rectángulo fuente
     * @param pos Posición en la X o la Y que se quiere colocar la imagen
     * @param axis  Eje en el que se centrará la imagen:
     *              axis == 0 eje X
     *              axis == 1 eje Y
     */
    public void drawCentered(Graphics g, Rect src, int pos, int axis){

        if (axis == 0)
            g.drawImageCenteredAxisX(_image, src, pos);
        else if (axis == 1){
            g.drawImageCenteredAxisY(_image, src, pos);
        }

    }

    public Image getImage(){
        return _image;
    }

    public Rect getRect(){
        return _rect;
    }

    private Image _image;

    private Rect _rect;

} // interface Sprite
