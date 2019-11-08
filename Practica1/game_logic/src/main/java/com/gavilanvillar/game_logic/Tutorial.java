package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGameState;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;

public class Tutorial extends AbstractGameState {
    public Tutorial (Game game){

        super(game);

    }

    public void init(ResourceManager resourceManager){

        super.init(resourceManager);

        int randomBackground = (int)Math.floor(Math.random() * _resourceManager.getBackgrounds().length);
        _actualBackground = _resourceManager.getBackgrounds()[randomBackground];
        _arrowsBackground = _resourceManager.getArrowsBackground();

    }

    @Override
    public void update(double deltaTime){

    }

    @Override
    public void render(){
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
}
