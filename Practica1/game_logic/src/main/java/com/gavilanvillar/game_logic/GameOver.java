package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Button;
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

    private final int SCORE_POS_Y = 800;
    private final int POINTS_POS_Y = 1050;
    private final float SCORE_SCALE = 1.5f;
    private final float POINTS_SCALE = 0.5f;
    private final int NUMBER_OF_LETTERS_POINTS = 6;

    private final int SOUND_ICON_POS_X = 30;
    private final int QUESTION_ICON_POS_X = 910;
    private final int ICON_POS_Y = 30;

    private final int FULLSCREEN_KEYCODE = 70; // f


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

        _soundMutedIcon = resourceManager.getMutedIcon();
        _soundUnMutedIcon = resourceManager.getNotMutedIcon();
        _soundButton = new Button(_soundUnMutedIcon);

        _questionIcon = resourceManager.getQuestionIcon();
        _instructionsButton = new Button(_questionIcon);

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de GameOver)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Realiza la renderizacion de los Sprites del GameState
    **/
    @Override
    public void render() {
        _game.getGraphics().clear(0xFF000000);

        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _playAgain.drawCentered(_game.getGraphics(), PLAY_AGAIN_POSY, 0, fadeInOutAlpha);
        _gameOver.drawCentered(_game.getGraphics(), GAME_OVER_POSY, 0, 1.0f);

        _soundButton.getSprite().draw(_game.getGraphics(), SOUND_ICON_POS_X, ICON_POS_Y,1.0f);
        _instructionsButton.getSprite().draw(_game.getGraphics(), QUESTION_ICON_POS_X, ICON_POS_Y, 1.0f);

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
                 // Se ha hecho click en el botón de sonido
                 if(_soundButton.isClicked(e._x,e._y))
                 {
                     if(_isSoundMuted) _soundButton.changeSprite(_soundUnMutedIcon);
                     else _soundButton.changeSprite(_soundMutedIcon);

                     _isSoundMuted = !_isSoundMuted;
                 }
                 // Se ha hecho click en el botón de instrucciones
                 else if(_instructionsButton.isClicked(e._x, e._y)){

                     _changeStateSound.play();
                     Tutorial s = new Tutorial(_game);
                     s.init(_resourceManager);
                     _game.getGameStateManager().setState(s);

                 }
                 else if (e._id == FULLSCREEN_KEYCODE){
                     _fullscreen = !_fullscreen;
                     _game.setFullscreen(_fullscreen);
                 }
                 // Se ha hecho click en la pantalla
                 else {
                     _changeStateSound.play();
                     SwitchDash s = new SwitchDash(_game);
                     s.init(_resourceManager);
                     _game.getGameStateManager().setState(s);
                 }
            }
        }

    }

    /**
     * Pinta la información sobre la puntuación final.
     */
    public void renderFinalScore(){
        renderScore();
        renderPoints();
    }

    /**
     * Pinta la puntuación.
     */
    private void renderScore(){

        int newScore = _score;
        boolean zeroScore = false;
        // Se calcula el cantidad de números que tiene "score" para poder centrarlo en pantalla
        int numCont = 0;
        while (newScore > 0 || (newScore == 0 && !zeroScore)){
            numCont++;
            newScore /= 10;
            if (newScore == 0) zeroScore = true;
        }

        // Se calcula la posición inicial del número
        int initX  = ((int)(_game.getGraphics().getResolutionWidth() / 2) +
                (int)(((_numbers[0].getSrcRect()._width * numCont) * SCORE_SCALE) / 2)) -
                (int)(_numbers[0].getSrcRect()._width * SCORE_SCALE);

        // Se guardan los valores del ancho y el alto de cada número
        int numberWidth = (int)(_numbers[0].getSrcRect()._width * SCORE_SCALE);
        int numberHeight = (int)(_numbers[0].getSrcRect()._height * SCORE_SCALE);

        // Pinta los números
        newScore = _score;
        for(int i = 0; i < numCont; i++){
            int n = newScore % 10;

            _numbers[n].draw(_game.getGraphics(), new Rect(initX, initX + numberWidth,
                    SCORE_POS_Y, SCORE_POS_Y + numberHeight), 1.0f);

            initX = initX - numberWidth;

            newScore /= 10;
        }

    }

    /**
     * Pinta la palabra, en este caso, POINTS
     */
    private void renderPoints(){

        // Calcula la posición inicial de la palabra
        int initX  = (int)(_game.getGraphics().getResolutionWidth() / 2) -
                (int)(((_letters[0].getSrcRect()._width * NUMBER_OF_LETTERS_POINTS) * POINTS_SCALE) / 2);

        // Se guardan los valores del ancho y el alto de cada letra
        int letterWidth = (int)(_letters[0].getSrcRect()._width * POINTS_SCALE);
        int letterHeight = (int)(_letters[0].getSrcRect()._height * POINTS_SCALE);

        // Pinta las letras de POINTS
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

    // Puntuación
    private int _score = 0;
    private Sprite[] _numbers = null;
    private Sprite[] _letters = null;

    private int[] _pointsLetters = { 15, 14, 8, 13, 19, 18 };

    // Botones
    private Button _soundButton = null;
    private Button _instructionsButton = null;

    private Sprite _soundMutedIcon = null;
    private Sprite _soundUnMutedIcon = null;

    private Sprite _questionIcon = null;

    private boolean _isSoundMuted = false;

    private boolean _fullscreen = false;

}
