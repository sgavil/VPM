package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

import java.util.List;

/**
 * Estado principal de la aplicación donde se van generando bolas que caen y se puede cambiar el color de la pala para recogerlas
 * y obtener puntos, si la bola no coincide en color el jugador perderá la partida y pasará al estado GameOver
 */
public class SwitchDash extends GenericGameState {

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

        _player = new Player(_resourceManager.getWhitePlayer(), _resourceManager.getBlackPlayer(), PLAYER_COLOR.WHITE);
        _ballsManager = new BallsManager(_resourceManager.getWhiteBall(), _resourceManager.getBlackBall(), _player);

        _ballsManager.setPlayer(_player);
        _ballsManager.setSwitchDash(this);

    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de SwitchDash)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Intercambia el color de la pala del jugador
     */
    public void swapPlayers() {
        _player.swapColor();
    }


    /**
     * LLama al update de la clase padre y al update del manager de bolas
     * @param deltaTime Tiempo en milisegundos desde el ultimo frame
     */
    @Override
    public void update(double deltaTime) {

        super.update(deltaTime);

        _ballsManager.update(deltaTime);

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
    }

    /**
     * Realiza la transición al estado GameOver, es llamado desde el BallsManager cuando una bola sobrepasa
     * la pala del jugador
     */
    public void gameOver() {
        GameOver gameOverState = new GameOver(_game,_arrowsVel);
        gameOverState.init(_resourceManager);
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
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO)
                swapPlayers();
            else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
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


}
