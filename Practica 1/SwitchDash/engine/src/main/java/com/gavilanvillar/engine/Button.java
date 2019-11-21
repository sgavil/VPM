package com.gavilanvillar.engine;

import java.util.List;

/**
 * Clase Button
 *
 * Clase que hace referencia a un botón en la aplicación, es respresentado por un sprite.
 *
 * Se puede saber si se ha pulsado dentro del area definida por el sprite
 *
 */
public class Button
{

    /**
     * Crea un boton asignandole un sprite.
     * @param s Sprite del boton
     */
    public Button(Sprite s){
        _sprite = s;
    }

    /**
     * Devuelve true si las coordenadas pasadas como parametro estan dentro de la
     * imagen de destino del sprite
     *
     * @param x Posicion x donde se ha pulsado
     * @param y Posicion y donde se ha pulsado
     * @return true si se ha pulsado el boton
     */
   public boolean isClicked(int x,int y)
    {
        Rect buttonArea = _sprite.getDestRect();
        return (x > buttonArea._left) && (x < buttonArea._right)
                && (y > buttonArea._top) && (y < buttonArea._bottom);
    }

    public Sprite getSprite(){
        return _sprite;
    }

    public void changeSprite(Sprite s){
        _sprite = s;
    }
    private Sprite _sprite;
}
