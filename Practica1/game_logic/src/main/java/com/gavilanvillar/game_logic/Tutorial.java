package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGameState;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;

public class Tutorial extends AbstractGameState {
    private final int TAP_TO_PLAY_POS_Y = 1464;
    private final int HOW_TO_PLAY_POS_Y = 290;
    private final int INSTRUCTIONS_POS_Y = 768;

    public Tutorial (Game game){

        super(game);

    }

    public void init(ResourceManager resourceManager){

        super.init(resourceManager);

        int randomBackground = (int)Math.floor(Math.random() * _resourceManager.getBackgrounds().length);
        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];
        _arrowsBackground = _resourceManager.getArrowsBackground();

        _howToPlay = _resourceManager.getHowToPlay();
        _instructions = _resourceManager.getInstructions();
    }

    @Override
    public void update(double deltaTime){

        fadeInFadeOutTap(deltaTime);
        arrowsMovement(deltaTime);

    }

    @Override
    public void render(){

        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _howToPlay.drawCentered(_game.getGraphics(), HOW_TO_PLAY_POS_Y, 0, 1);
        _instructions.drawCentered(_game.getGraphics(), INSTRUCTIONS_POS_Y, 0, 1);

        _tapToPlay.drawCentered(_game.getGraphics(), 1464, 0, tapAlpha);
    }

    @Override
    public void handleEvent() {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e: ev){
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO)
            {
                SwitchDash s = new SwitchDash(_game);
                s.init(_resourceManager);
                _game.getGameStateManager().setState(s);
            }
            else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }
    }

    private Sprite _howToPlay = null;
    private Sprite _instructions = null;
}
