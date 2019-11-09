package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sprite;

/**
 * Clase que encapsula todos los elementos que representan la pala del jugador:
 * Color de la pala, sprite actual y posibles sprites que puede utilizar.
 * Se encarga de su propio renderizado
 */
public class Player {
    private final int PLAYER_POS_Y = 1200;

    public enum PLAYER_COLOR {
        BLACK, WHITE
    }

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

    /**
     * Renderizado del sprite del jugador
     * @param g Graphics para poder realizar el pintado
     */
    public void render(Graphics g){
        _sprite.drawCentered(g, PLAYER_POS_Y, 0, 1.0f);
    }

    /**
     * Cambio de color y sprite del jugador
     */
    public void swapColor(){
        if (this._playerColor == PLAYER_COLOR.BLACK) {
            this._playerColor = PLAYER_COLOR.WHITE;
            this._sprite = _whiteSprite;
        }
        else{
            this._playerColor = PLAYER_COLOR.BLACK;
            this._sprite = _blackSprite;
        }
    }

    /**
     * @return Devuelve el sprite actual del jugador
     */
    public Sprite getSprite(){
        return _sprite;
    }

    /**
     * @return Devuelve el color actual del jugador
     */
    public PLAYER_COLOR getColor(){
        return _playerColor;
    }

    private Sprite _sprite;

    private Sprite _whiteSprite;
    private Sprite _blackSprite;

    private PLAYER_COLOR _playerColor;
}
