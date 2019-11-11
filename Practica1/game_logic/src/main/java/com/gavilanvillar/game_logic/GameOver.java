package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;
import java.util.logging.Level;

/**
 * Estado del juego al que se llega al perder ( una bola sobrepasa la pala del jugador )
 */
public class GameOver extends GenericGameState {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos constantes (de GameOver)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //Posiciones de los Sprites
    private final int PLAY_AGAIN_POSY = 1396;
    private final int GAME_OVER_POSY = 364;

    private final int SCORE_POS_Y = 500;
    private final int POINTS_POS_Y = 1100;
    private final float SCORE_SCALE = 1.5f;
    private final float POINTS_SCALE = 0.5f;
    private final int NUMBER_OF_LETTERS_POINTS = 6;



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //  Métodos de inicialización (de GameOver)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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

        _numbers = resourceManager.getNumbers();
        _letters = resourceManager.getLetters();

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de GameOver)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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

        renderFinalScore();
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

    public void renderFinalScore(){
        renderScore();
        renderPoints();
    }

    private void renderScore(){

    }

    private void renderPoints(){

        int initX  = (int)(_game.getGraphics().getWidth() / 2) -
                (int)(((_letters[0].getSrcRect()._width * NUMBER_OF_LETTERS_POINTS) * POINTS_SCALE) / 2);


        int letterWidth = (int)(_letters[0].getSrcRect()._width * POINTS_SCALE);
        int letterHeight = (int)(_letters[0].getSrcRect()._height * POINTS_SCALE);

        for(int i = 0; i < _pointsLetters.length; i++){
            _letters[_pointsLetters[i]].draw(_game.getGraphics(), new Rect(initX, initX + letterWidth,
                    POINTS_POS_Y, POINTS_POS_Y + letterHeight), 1.0f);

            initX = initX + letterWidth;
        }

    }

    public void setScore(int s){
        _score = s;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de GameOver)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Sprite _gameOver = null;
    private Sprite _playAgain = null;

    private int _score = 0;
    private Sprite[] _numbers = null;
    private Sprite[] _letters = null;

    private int[] _pointsLetters = { 15, 14, 8, 13, 19, 18 };

}
