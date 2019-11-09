package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;

/**
 * Estado del juego al que se llega al perder ( una bola sobrepasa la pala del jugador )
 */
public class GameOver extends GenericGameState {

    //Posiciones de los Sprites
    private final int PLAY_AGAIN_POSY = 1396;
    private final int GAME_OVER_POSY = 364;

    /**
     * Construtor del estado GameOver
     * @param game Referencia al juego para poder obtener los "singleton" graphics,input y el stateManager
     * @param arrowSpeed Velocidad a la que se estaban moviendo las flechas en el estado de juego para mantenerla en este etado
     */
    public GameOver(Game game,int arrowSpeed){

        super(game);
        _arrowsVel = arrowSpeed;
    }


    /**
     * Se llama al init de GenericGameState y se obtienen los sprites específicos de este estado
     * @param resourceManager Gestor de recursos para poder obtener los sprites
     */
    public void init(ResourceManager resourceManager) {

        super.init(resourceManager);

        _gameOver = _resourceManager.getGameOver();
        _playAgain = resourceManager.getPlayAgain();

    }


    /**
     * Realiza la renderizacion de los Sprites del GameState
    **/
    @Override
    public void render() {
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _playAgain.drawCentered(_game.getGraphics(), PLAY_AGAIN_POSY, 0, fadeInOutAlpha);
        _gameOver.drawCentered(_game.getGraphics(), GAME_OVER_POSY, 0, 1.0f);
    }

    /**
     * Gestiona la pulsacion en la ventana para volver a jugar cambiando al estado SwitchDash
     */
    @Override
    public void handleEvent() {

        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e: ev){
             if (e._type == EventType.LIBERADO) {
                SwitchDash s = new SwitchDash(_game);
                s.init(_resourceManager);
                _game.getGameStateManager().setState(s);
            }
        }

    }


    //Sprites del estado
    private Sprite _gameOver = null;
    private Sprite _playAgain = null;


}
