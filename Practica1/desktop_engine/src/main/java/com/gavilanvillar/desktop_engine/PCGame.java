package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Audio;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.GameState;
import com.gavilanvillar.engine.GameStateManager;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;


/**
 * Clase PCGame
 *
 * Clase que implementa el bucle principal del juego
 */
public class PCGame implements Game {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos de inicialización (PCGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public PCGame(){

    }

    /**
     * Inicializacion del PCGame
     * @param window Ventana donde se va a pintar la aplicación
     * @param gameState Estado que se va a ejecutar
     */
    public void init(PCWindow window, GameState gameState){
        this._window = window;

        this._gameStateManager = new GameStateManager();
        this._gameStateManager.setState(gameState);


        this._graphics = new PCGraphics(window);
        this._window.setGraphics(this._graphics);

        this._input = new PCInput();
        this._input.init();

        this._audio = new PCAudio();

        //Se añaden los listener del ratón
        this._window.getContentPane().addMouseListener(this._input.getMouseController());
        this._window.getContentPane().addMouseMotionListener(this._input.getMouseController());


    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Getters y Setters ( de PCGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public GameStateManager getGameStateManager() {
        return _gameStateManager;
    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    @Override
    public Audio getAudio() {
        return _audio;
    }

    //@Override
    public void run() {
        // Vamos allá.
        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;


        // Bucle principal
        while(true) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            _gameStateManager.getActualState().update(elapsedTime);
            _gameStateManager.getActualState().handleEvent();

            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
            // Pintamos el frame con el BufferStrategy
            do {
                do {
                    java.awt.Graphics graphics = _window.getStrategy().getDrawGraphics();
                    _graphics.setGraphics(graphics);
                    try {
                        _gameStateManager.getActualState().render();
                    }
                    finally {
                        graphics.dispose();
                    }
                } while(_window.getStrategy().contentsRestored());
                _window.getStrategy().show();
            } while(_window.getStrategy().contentsLost());
        } // while
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de PCGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static PCWindow _window = null;
    private static GameStateManager _gameStateManager = null;
    private static PCGraphics _graphics = null;
    private static PCAudio _audio = null;
    private static PCInput _input = null;
}
