package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Ball.BALL_COLOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BallsManager {
    final int BALL_SEPARATION = 395;
    final int INITIAL_BALL_VEL = 430;

    final int SAME_COLOR_PERCENTAGE = 70;

    public BallsManager(Sprite whiteSprite, Sprite blackSprite, Player player) {
        this._whiteSprite = whiteSprite;
        this._blackSprite = blackSprite;

        setPlayer(player);

        this._objs = new ArrayList<>();

        this._ball = getObject();

        _ballVel = INITIAL_BALL_VEL;

    }

    private BALL_COLOR getNextColor(int i) {

        BALL_COLOR bColor;

        if (i < _objs.size() && !_objs.isEmpty()) {
            boolean preColorProbability = new Random().nextInt(100) < SAME_COLOR_PERCENTAGE;
            BALL_COLOR differentColor = (_ball.getBallColor() == BALL_COLOR.BLACK)
                    ? BALL_COLOR.WHITE : BALL_COLOR.BLACK;


            bColor = (preColorProbability) ?
                    _ball.getBallColor() : differentColor;
        } else {
            int randomColor = (int) Math.floor(Math.random() * 2);
            bColor = (randomColor == 0) ? BALL_COLOR.BLACK : BALL_COLOR.WHITE;
        }

        return bColor;
    }

    public Ball getObject() {
        int i = 0;
        while (i < _objs.size() && _objs.get(i).isActive()) {
            i++;
        }


        BALL_COLOR bColor = getNextColor(i);
        Ball b = null;


        if (i >= _objs.size()) {

            b = (bColor == BALL_COLOR.BLACK) ?
                    new Ball(_blackSprite, bColor) : new Ball(_whiteSprite, bColor);


            b.setActive(true);

            //initializeObject(a);
            _objs.add(b);
        } else {

            b = _objs.get(i);

            b.setColor(bColor);

            b.setSprite((bColor == BALL_COLOR.BLACK) ? _blackSprite : _whiteSprite);
            b.setActive(true);

        }

        return b;
    }

    private void generateBall() {
        _lastGeneratedY = _ball.getPosY();

        if (_lastGeneratedY >= BALL_SEPARATION) {
            _ball = getObject();
            _ball.setPosY(0);
        }
    }

    public void update(double deltaTime) {

        generateBall();

        for (int i = 0; i < _objs.size(); i++) {

            if (_objs.get(i).isActive()) {

                if (_objs.get(i).checkCollisionWith(_player)) {
                    _objs.get(i).setActive(false);
                }

                int nextPos = _objs.get(i).getPosY();
                nextPos += (_ballVel * deltaTime);
                _objs.get(i).setPosY(nextPos);

            }

        }

    }

    public void render(Graphics g) {
        for (Ball b : _objs) {
            if (b.isActive()) {
                b.getBallSprite().drawCentered(g, b.getPosY(), 0, 1.0f);
            }
        }

    }

    public void setPlayer(Player p) {

        this._player = p;

    }

    private List<Ball> _objs;
    private Sprite _whiteSprite;
    private Sprite _blackSprite;

    private Player _player;

    private int _ballVel = 0;

    private int _lastGeneratedY;

    private Ball _ball = null;
}
