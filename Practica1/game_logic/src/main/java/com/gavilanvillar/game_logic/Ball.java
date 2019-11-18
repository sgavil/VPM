package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

/**
 * Clase que representa una bola del juego. Agrupa un Sprite, un color, una posicion en el eje Y
 * , ademas del estado activo/inactivo
 */
public class Ball
{
    /**
     * Distintos colores posibles para las bolas
     */
    public enum BALL_COLOR {
        BLACK, WHITE
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Metodos de inicialización (de Ball)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Construye un objeto de tipo Ball
     * @param sprite Sprite de la bola que se quiere constuir
     * @param ballColor Color a nivel logico de la bola
     */
    public Ball(Sprite sprite, BALL_COLOR ballColor) {
        _sprite = new Sprite(sprite.getImage(), sprite.getSrcRect());
        _ballColor = ballColor;
        _alpha = 1.0f;

    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Getters y Setters (de Ball)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    /**
     * Actualiza la posicion en Y del objeto
     * @param y Posicion a la que se quiere actualizar el valor de la bola
     */
    public void setPosY(int y) {

        _posY = y;
    }

    /**
     * Actualiza la posicion en X del objeto
     * @param x Posicion a la que se quiere actualizar el valor de la bola
     */
    public void setPosX(int x){
        _posX = x;
    }

    /**
     * Actualiza la velocidad en Y del objeto
     * @param y Velocidad a la que se quiere actualizar el valor de la bola
     */
    public void setVelY(int y){
        _velY = y;
    }

    /**
     * Actualiza la velocidad en X del objeto
     * @param x Velocidad a la que se quiere actualizar el valor de la bola
     */
    public void setVelX(int x){
        _velX = x;
    }

    /**
     * @return Devuelve la posicion en el eje Y del objeto
     */
    public int getPosY() {

        return _posY;
    }

    /**
     * Devuelve la posición en el eje X del objeto
     *
     * @return posición X
     */
    public int getPosX(){

        return _posX;
    }

    /**
     * Devuelve la velocidad en el eje Y del objeto
     *
     * @return velocidad Y
     */
    public int getVelY(){
        return _velY;
    }

    /**
     * Devuelve la velocidad en el eje X del objeto
     *
     * @return velocidad X
     */
    public int getVelX(){
        return _velX;
    }

    /**
     *
     * @return Devuelve el Sprite que representa a la bola
     */
    public Sprite getBallSprite() {

        return _sprite;
    }

    /**
     * Activa o desactiva el objeto
     * @param a Valor al que se quiere poner el objeto (activado o desactivado)
     */
    public void setActive(boolean a){

        _active = a;
    }

    /**
     * Permite cambiar el sprite actual de la bola
     * @param s Sprite por el que se quiere sustituir el sprite actual
     */
    public void setSprite(Sprite s){

        _sprite = new Sprite(s.getImage(), s.getSrcRect());
    }

    /**
     * Actualiza el color de la bola
     * @param c Color al que se cambia el valor actual de la bola
     */
    public void setColor(BALL_COLOR c){
        _ballColor = c;
    }

    /**
     * Actualiza el valor alpha usado para renderizar
     * @param alpha alpha
     */
    public void setAlpha(float alpha){
        this._alpha = alpha;
    }

    /**
     * Actualiza el valor del tamaño de la bola
     * @param size tamaño
     */
    public void setSize(int size){
        this._size = size;
    }

    /**
     *
     * @return Devuelve el color actual de la bola
     */
    public BALL_COLOR getBallColor(){

        return _ballColor;

    }

    /**
     *
     * @return Devuelve true si el objeto esta activo
     */
    public boolean isActive(){

        return _active;
    }

    /**
     * Devuelve el valor alpha de la bola
     * @return alpha
     */
    public float getAlpha(){
        return _alpha;
    }

    /**
     * Devuelve el tamaño de la bola
     * @return tamaño
     */
    public int getSize(){
        return _size;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Metodos públicos (de Ball)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Comprueba si la bola ha "colisionado"
     * @param player Objeto con el que se comprueba la colision
     * @return true si ha colisionado y tienen el mismo color, false en caso de no haber colisionado o no tener el mismo color
     */
    public boolean checkCollisionWith(Player player){

        return ( (_posY + getBallSprite().getSrcRect()._height >= player.getPosY() &&
                _posY <= player.getPosY() + player.getSprite().getDestRect()._height));

    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Atributos privados (de Ball)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Sprite _sprite;
    private BALL_COLOR _ballColor;

    private int _posY = 0;
    private int _posX = 0;
    private int _velX = 0;
    private int _velY = 0;

    private int _size = 0;
    private float _alpha;

    //Atributo que indica si el objeto se actualiza de forma lógica y de forma gráfica
    private boolean _active = true;

}
