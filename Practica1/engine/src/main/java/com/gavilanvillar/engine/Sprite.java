package com.gavilanvillar.engine;

public class Sprite {

    public Sprite(Image image, Rect rect){
        this._image = image;
        this._rect = rect;
    }

    public void draw(Graphics g, int x, int y){
        g.drawImage(_image, x, y);
    }

    public void draw(Graphics g, Rect src, int x, int y){
        g.drawImage(_image, src, x, y);
    }

    public void draw(Graphics g, Rect src, Rect dest){
        g.drawImage(_image, src, dest);
    }

    public void drawCentered(Graphics g, Rect src){
        g.drawImageCentered(_image, src);
    }

    /**
     * Método para pintar centrado en alguno de los dos ejes, X o Y
     *
     * @param g Graphics
     * @param src Rectángulo fuente
     * @param pos Posición en la X o la Y que se quiere colocar la imagen
     * @param axis  Eje en el que se centrará,
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
