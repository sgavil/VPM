package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGameState;
import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.GameState;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Input;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;

public class SwitchDash extends AbstractGameState {


    public SwitchDash(Game game) {

        super(game);

    }

    public void init(ResourceManager resourceManager) {
        super.init(resourceManager);

        int randomBackground = (int) Math.floor(Math.random() * _resourceManager.getBackgrounds().length);

        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];
        _actualPlayer = _resourceManager.getWhitePlayer();

        BALLS_MANAGER = new BallsManager(_resourceManager.getWhiteBall(),_resourceManager.getBlackBall(),PLAYER_POS_Y);

    }

    public void swapPlayers() {
        _actualPlayer = (_actualPlayer == _resourceManager.getWhitePlayer()) ?
                _resourceManager.getBlackPlayer() : _resourceManager.getWhitePlayer();
    }


    @Override
    public void update(double deltaTime) {

        arrowsMovement(deltaTime);
        BALLS_MANAGER.ballsUpdate(deltaTime,_ballsSpeed);

    }

    @Override
    public void render() {
        _game.getGraphics().clear(0xFF000000);
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);
        _actualPlayer.drawCentered(_game.getGraphics(), PLAYER_POS_Y, 0, 1.0f);


        //Balls Render
        BALLS_MANAGER.ballsRenderer(_game.getGraphics());
    }

    @Override
    public void handleEvent() {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e : ev) {
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO)
                swapPlayers();
            else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }
    }


    private Sprite _actualBackground = null;
    private Sprite _actualPlayer = null;

    private int _ballsSpeed = 430;

    private final int PLAYER_POS_Y = 1200;
    private BallsManager BALLS_MANAGER;
}
