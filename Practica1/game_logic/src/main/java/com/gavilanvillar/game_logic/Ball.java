package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

public class Ball {
    public enum BALL_COLOR {
        BLACK, WHITE
    }

    public Ball(Sprite sprite, BALL_COLOR ballColor) {
        _sprite = new Sprite(sprite.getImage(), sprite.getSrcRect());
        _ballColor = ballColor;
    }

    public void setPosY(int y) {

        _posY = y;
    }

    public int getPosY() {

        return _posY;
    }

    public Sprite getBallSprite() {

        return _sprite;
    }

    public boolean checkCollisionWith(Player player){

        return ((_sprite.getDestRect()._bottom >= player.getSprite().getDestRect()._top) &&
                (_sprite.getDestRect()._bottom <= player.getSprite().getDestRect()._bottom) &&
                ((player.getColor() == PLAYER_COLOR.BLACK && _ballColor == BALL_COLOR.BLACK) ||
                        (player.getColor() == PLAYER_COLOR.WHITE && _ballColor == BALL_COLOR.WHITE)));

    }

    public boolean isActive(){

        return _active;
    }

    public void setActive(boolean a){

        _active = a;
    }

    public void setSprite(Sprite s){

        _sprite = new Sprite(s.getImage(), s.getSrcRect());
    }

    public void setColor(BALL_COLOR c){
        _ballColor = c;
    }

    public BALL_COLOR getBallColor(){

        return _ballColor;

    }


    private Sprite _sprite;
    private BALL_COLOR _ballColor;
    private int _posY = 0;

    private boolean _active = true;

}
