package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Ball.BALL_COLOR;

public class Player {
    private final int PLAYER_POS_Y = 1200;

    public enum PLAYER_COLOR {
        BLACK, WHITE
    }

    public Player(Sprite whiteSprite, Sprite blackSprite, PLAYER_COLOR playerColor) {
        this._playerColor = playerColor;
        this._whiteSprite = whiteSprite;
        this._blackSprite = blackSprite;
        this._sprite = (this._playerColor == PLAYER_COLOR.BLACK) ?
                this._blackSprite : this._whiteSprite;
    }

    public void update(double deltaTime){

    }

    public void render(Graphics g){
        _sprite.drawCentered(g, PLAYER_POS_Y, 0, 1.0f);
    }

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

    public Sprite getSprite(){
        return _sprite;
    }

    public PLAYER_COLOR getColor(){
        return _playerColor;
    }

    private Sprite _sprite = null;
    private Sprite _whiteSprite = null;
    private Sprite _blackSprite = null;

    private PLAYER_COLOR _playerColor;
}
