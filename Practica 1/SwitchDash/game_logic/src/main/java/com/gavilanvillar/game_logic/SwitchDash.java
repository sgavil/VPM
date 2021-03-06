package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Music;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sound;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

import java.util.List;

/**
 * Estado principal de la aplicación donde se van generando bolas que caen y se puede cambiar el color de la pala para recogerlas
 * y obtener puntos, si la bola no coincide en color el jugador perderá la partida y pasará al estado GameOver
 */
public class SwitchDash extends GenericGameState {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos constantes (de SwitchDash)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private final int PLAYER_POS_Y = 1200;

    private final int NUMBERS_SEPARATION = 90;
    private final int SCORE_POS_X = 950;
    private final int SCORE_POS_Y = 30;
    private final float SCORE_SCALE = 1.0f;

    private final int FULLSCREEN_KEYCODE = 70; // f


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de inicialización (de SwitchDash)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public SwitchDash(Game game) {

        super(game);

    }

    /**
     * Se llama al init de GenericGameState y se obtienen los sprites específicos de este estado
     * Se realiza la inicializacion del BallsManager, se le pasa la referencia al player y a este estado
     * @param resourceManager Gestor de recursos para poder obtener los sprites
     */
    public void init(ResourceManager resourceManager) {
        super.init(resourceManager);

        _gameTheme = resourceManager.getSwitchDashTheme();
        _player = new Player(_resourceManager.getWhitePlayer(), _resourceManager.getBlackPlayer(), PLAYER_COLOR.WHITE);
        _player.setPosY(PLAYER_POS_Y);
        _changePlayer = resourceManager.getChangePlayer();


        _ballsManager = new BallsManager(_resourceManager.getWhiteBall(), _resourceManager.getBlackBall(), _player);

        _ballsManager.setCollSound(resourceManager.getTakeBall());
        _ballsManager.setPlayer(_player);
        _ballsManager.setSwitchDash(this);

        _numbers = resourceManager.getNumbers();

        _gameTheme.setLoop(true);
        _gameTheme.play();

    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de SwitchDash)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Intercambia el color de la pala del jugador
     */
    public void swapPlayers() {

        _player.swapColor();
        _changePlayer.play();
    }


    /**
     * LLama al update de la clase padre y al update del manager de bolas
     * @param deltaTime Tiempo en milisegundos desde el ultimo frame
     */
    @Override
    public void update(double deltaTime) {

        super.update(deltaTime);

        _ballsManager.update(deltaTime);

        if(!alreadyPlayed){
            _gameTheme.play();
            alreadyPlayed = true;
        }

    }

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

        _player.render(_game.getGraphics());

        //Balls Render
        _ballsManager.render(_game.getGraphics());

        renderScore();
    }

    /**
     * Pinta la puntuación actual
     */
    public void renderScore(){

        int m = 0;
        int separation = (int)(NUMBERS_SEPARATION * SCORE_SCALE);

        boolean zeroScore = false;

        int newScore = _score;
        while(newScore > 0 || (newScore == 0 && !zeroScore)){
            int n = newScore % 10;
            _numbers[n].draw(_game.getGraphics(), new Rect(SCORE_POS_X - (separation * m),
                    SCORE_POS_X - (separation * m) + (int)(SCORE_SCALE * _numbers[n].getSrcRect()._width),
                    SCORE_POS_Y,
                    SCORE_POS_Y + (int)(SCORE_SCALE * _numbers[n].getSrcRect()._height)), 1.0f);
            newScore /= 10;
            m++;
            if (newScore == 0) zeroScore = true;
        }

    }

    /**
     * Realiza la transición al estado GameOver, es llamado desde el BallsManager cuando una bola sobrepasa
     * la pala del jugador
     */
    public void gameOver() {
        GameOver gameOverState = new GameOver(_game,_arrowsVel);
        gameOverState.init(_resourceManager);
        gameOverState.setScore(_score);
        gameOverState.changeMutedIcon();
        _gameTheme.stop();
        _game.getGameStateManager().setState(gameOverState);
    }

    /**
     * Gestiona la pulsacion en la ventana.
     * Cuando se realiza una pulsacion se cambia el color de la pala del jugador
     */
    @Override
    public void handleEvent() {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e : ev) {
            if (e._type == EventType.LIBERADO) {
                if (e._id == FULLSCREEN_KEYCODE){
                    _fullscreen = !_fullscreen;
                    _game.setFullscreen(_fullscreen);
                }
                else {
                    swapPlayers();
                }

            }
        }
    }

    /**
     * Aumenta en 1 la puntuacion del jugador
     */
    public void addScore() {
        _score++;
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de SwitchDash)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Player _player = null;
    private BallsManager _ballsManager = null;

    //Puntuacion actual del jugador
    private int _score = 0;
    private Sprite[] _numbers = null;

    private Sound _changePlayer ;

    private Music _gameTheme;

    private  boolean alreadyPlayed = false;
    private boolean _fullscreen = false;


}
