package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sprite;

/**
 * Clase que encapsula todos los elementos que representan la pala del jugador:
 * Color de la pala, sprite actual y posibles sprites que puede utilizar.
 * Se encarga de su propio renderizado
 */
public class Player {



    public enum PLAYER_COLOR {
        BLACK, WHITE
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos constantes (de Player)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de inicialización  (de Player)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * @param whiteSprite Sprite blanco de la pala
     * @param blackSprite Sprite negro de la pala
     * @param playerColor Color con el que se va a iniciar el jugador
     */
    public Player(Sprite whiteSprite, Sprite blackSprite, PLAYER_COLOR playerColor) {
        this._playerColor = playerColor;
        this._whiteSprite = whiteSprite;
        this._blackSprite = blackSprite;
        this._sprite = (this._playerColor == PLAYER_COLOR.BLACK) ?
                this._blackSprite : this._whiteSprite;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de Player)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Renderizado del sprite del jugador
     *
     * @param g Graphics para poder realizar el pintado
     */
    public void render(Graphics g) {
        _sprite.drawCentered(g, posY, 0, 1.0f);
    }

    /**
     * Cambio de color y sprite del jugador
     */
    public void swapColor() {
        if (this._playerColor == PLAYER_COLOR.BLACK) {
            this._playerColor = PLAYER_COLOR.WHITE;
            this._sprite = _whiteSprite;
        } else {
            this._playerColor = PLAYER_COLOR.BLACK;
            this._sprite = _blackSprite;
        }
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Getters y Setters  (de Player)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * @return Devuelve el sprite actual del jugador
     */
    public Sprite getSprite() {
        return _sprite;
    }

    /**
     * @return Devuelve el color actual del jugador
     */
    public PLAYER_COLOR getColor() {
        return _playerColor;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de Player)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Sprite _sprite;

    private Sprite _whiteSprite;
    private Sprite _blackSprite;

    private PLAYER_COLOR _playerColor;

    private int posY;
}
