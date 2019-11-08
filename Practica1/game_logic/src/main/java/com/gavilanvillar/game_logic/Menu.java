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
    }



    @Override
    public void update(double deltaTime) {

        fadeInFadeOutTap(deltaTime);
        arrowsMovement(deltaTime);
    }

    @Override
    public void render() {
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _switchDashLogo.drawCentered(_game.getGraphics(), 356, 0, 1.0f);
        _tapToPlay.drawCentered(_game.getGraphics(), 950, 0, tapAlpha);
    }

    @Override
    public void handleEvent() {

        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e: ev){
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO) {
                Tutorial s = new Tutorial(_game);
                s.init(_resourceManager);
                _game.getGameStateManager().setState(s);
            }
            else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }

    }


    private Sprite _actualBackground = null;
    private Sprite _switchDashLogo = null;

}
