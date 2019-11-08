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
import com.gavilanvillar.game_logic.Ball.BALL_COLOR;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

import java.util.List;

public class SwitchDash extends AbstractGameState {

    public SwitchDash(Game game) {

        super(game);

    }

    public void init(ResourceManager resourceManager) {
        super.init(resourceManager);

        int randomBackground = (int)Math.floor(Math.random() * _resourceManager.getBackgrounds().length);
        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];
        _actualPlayer = _resourceManager.getWhitePlayer();

        _player = new Player(_resourceManager.getWhitePlayer(), _resourceManager.getBlackPlayer(), PLAYER_COLOR.WHITE);
        _ballsManager = new BallsManager(_resourceManager.getWhiteBall(),_resourceManager.getBlackBall(), _player);

    }

    public void swapPlayers() {
        _player.swapColor();
    }


    @Override
    public void update(double deltaTime) {

        arrowsMovement(deltaTime);
        _ballsManager.update(deltaTime);

    }

    @Override
    public void render() {
        _game.getGraphics().clear(0xFF000000);
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);


        _player.render(_game.getGraphics());

        //Balls Render
        _ballsManager.setPlayer(_player);
        _ballsManager.render(_game.getGraphics());
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

    private Player _player = null;
    private BallsManager _ballsManager = null;
}
