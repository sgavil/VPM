package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGameState;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;


public class Menu extends AbstractGameState {

    public Menu(Game game){
        super(game);
    }


    public void init(ResourceManager resourceManager) {
        super.init(resourceManager);

        int randomBackground = (int)Math.floor(Math.random() * _resourceManager.getBackgrounds().length);
        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];


        _switchDashLogo = _resourceManager.getSwitchDashLogo();
        _tapToPlay = _resourceManager.getTapToPlay();

        _arrowsBackground = _resourceManager.getArrowsBackground();
        _arrowsPosY_0 = 0 - _arrowsBackground.getImage().getHeight();
        _arrowsPosY_1 = 0;

    }


    private void arrowsMovement(double deltaTime){
        int newArrowsPos = 0;
        newArrowsPos += (_arrowsVel * deltaTime);
        _arrowsPosY_0 += newArrowsPos;
        _arrowsPosY_1 += newArrowsPos;

        if(_arrowsPosY_0 >= HEIGHT_RES && _arrowsPosY_1 >= 0)
            _arrowsPosY_0 = _arrowsPosY_1 - _arrowsBackground.getImage().getHeight();
        if(_arrowsPosY_1 >= HEIGHT_RES && _arrowsPosY_0 >= 0)
            _arrowsPosY_1 = _arrowsPosY_0 - _arrowsBackground.getImage().getHeight();
    }

    void fadeInFadeOutTap(double deltaTime){
        if(tapAlpha +(deltaAlpha * deltaTime) >= 1.0f) {
            fadeOut = true;
            fadeIn = false;

        }
        else if (tapAlpha - (deltaAlpha * deltaTime) <= 0.0f) {
            fadeOut = false;
            fadeIn = true;
        }


        if(fadeOut)
            tapAlpha -= (deltaAlpha * deltaTime);
        else if (fadeIn)
            tapAlpha += (deltaAlpha * deltaTime);

        System.out.print(tapAlpha + "\n");
    }

    @Override
    public void update(double deltaTime) {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        fadeInFadeOutTap(deltaTime);
        arrowsMovement(deltaTime);
    }

    @Override
    public void render(double deltaTime) {
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _switchDashLogo.drawCentered(_game.getGraphics(), 356, 0, 1.0f);
        _tapToPlay.drawCentered(_game.getGraphics(), 950, 0, tapAlpha);
    }

    private float deltaAlpha = 1.2f;
    private float tapAlpha = 1.0f;
    private boolean fadeIn = false;
    private boolean fadeOut = true;

    private float _arrowsVel = 750.0f;
    private int _arrowsPosY_0 = 0;
    private int _arrowsPosY_1 = 0;

    private Sprite _arrowsBackground = null;
    private Sprite _actualBackground = null;
    private Sprite _switchDashLogo = null;
    private Sprite _tapToPlay = null;
}
