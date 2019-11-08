package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BallsManager  {

    public BallsManager(Sprite whiteSprite,Sprite blackSprite, int playerCollisionY){
        _whiteSprite = whiteSprite;
        _blackSprite = blackSprite;

        _playerCollY = playerCollisionY;

        _objs = new ArrayList<>();

        _ball = getObject(Ball.BALL_COLOR.BLACK);
    }

    public Ball getObject(Ball.BALL_COLOR _bColor){
        int i = 0;
        while (i < _objs.size() && _objs.get(i).isActive()) {
            i++;
        }

        Ball b = null;
        if ( i >= _objs.size())
        {
            if(_bColor == Ball.BALL_COLOR.BLACK){

                b = new Ball(_blackSprite,_bColor);
            }
            else b = new Ball(_whiteSprite,_bColor);

            //initializeObject(a);
            _objs.add(b);
        } else {
            b = _objs.get(i);
            Sprite s;
            if(_bColor == Ball.BALL_COLOR.BLACK) s = _blackSprite;
            else s = _whiteSprite;
            b.setSprite(s);
            b.setActive(true);
        }

        return b;
    }


    public void ballsUpdate(double deltaTime,int ballsVelocity)
    {
        _lastGeneratedY = _ball.getPosY();

        if(_lastGeneratedY >= 395 )
        {
            _ball = getObject(Ball.BALL_COLOR.WHITE);
            _ball.setPosY(0);
        }

        for(Ball b : _objs){
            if(b.isActive()){
                int nextPos = b.getPosY();
                nextPos += (ballsVelocity *deltaTime);
                b.setPosY(nextPos);

                if( b.checkCollisionWith(_playerCollY)){
                    b.setActive(false);
                }
            }
        }



    }
    public void ballsRenderer(Graphics g)
    {
        for(Ball b : _objs)
        {
            if(b.isActive()){
                b.getBallSprite().drawCentered(g, b.getPosY(), 0, 1.0f);
            }
        }

    }
    private List<Ball> _objs;
    private Sprite _whiteSprite;
    private Sprite _blackSprite;

    private int _playerCollY;

    private int _lastGeneratedY;

    Ball _ball;
}
