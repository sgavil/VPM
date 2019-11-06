package com.gavilanvillar.game_logic;

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

public class SwitchDash implements GameState {
    private final int WIDTH_RES = 1080;
    private final int HEIGHT_RES = 1920;



    public SwitchDash(Game game) {

        this._game = game;

    }

    public void init(ResourceManager resourceManager) {
        this._resourceManager = resourceManager;
        
        ((AbstractGraphics) _game.getGraphics()).setLogicResolution(WIDTH_RES, HEIGHT_RES);

        int randomBackground = (int)Math.floor(Math.random() * _resourceManager.getBackgrounds().length);

        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];
        _arrowsBackground = _resourceManager.getArrowsBackground();
        _actualPlayer = _resourceManager.getWhitePlayer();

        arrowsPosY_0 = 0 - _arrowsBackground.getImage().getHeight();
        arrowsPosY_1 = 0;
    }



    public void orientationChanged(boolean portrait) {
        ((AbstractGraphics) _game.getGraphics()).swapPhysicResolution();
    }

    private void arrowsMovement(double deltaTime){
        int newArrowsPos = 0;
        newArrowsPos += (750 * deltaTime);
        arrowsPosY_0 += newArrowsPos;
        arrowsPosY_1 += newArrowsPos;

        if(arrowsPosY_0 >= HEIGHT_RES && arrowsPosY_1 >= 0)
            arrowsPosY_0 = arrowsPosY_1 - _arrowsBackground.getImage().getHeight();
        if(arrowsPosY_1 >= HEIGHT_RES && arrowsPosY_0 >= 0)
            arrowsPosY_1 = arrowsPosY_0 - _arrowsBackground.getImage().getHeight();
    }

    public void swapPlayers(){
        _actualPlayer = (_actualPlayer == _resourceManager.getWhitePlayer()) ?
                _resourceManager.getBlackPlayer() : _resourceManager.getWhitePlayer();
    }

    @Override
    public void update(double deltaTime) {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e: ev){
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO)
                swapPlayers();
            else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }
        arrowsMovement(deltaTime);

    }

    @Override
    public void render(double deltaTime) {
        _game.getGraphics().clear(0x00000FF);
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);
        _arrowsBackground.drawCentered(_game.getGraphics(), arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), arrowsPosY_1, 0, 0.3f);
        //_blackBall.drawCentered(_game.getGraphics(), posY, 0, 1.0f);
        _actualPlayer.drawCentered(_game.getGraphics(), 1200, 0, 1.0f);
    }

    private Game _game;
    private ResourceManager _resourceManager;

    private int posX = 100;
    private int arrowsPosY_0 = 0;
    private int arrowsPosY_1 = 0;
    private int nextArrowsY = 0;

    private Sprite _actualBackground = null;
    private Sprite _arrowsBackground = null;
    private Sprite _actualPlayer = null;
}
