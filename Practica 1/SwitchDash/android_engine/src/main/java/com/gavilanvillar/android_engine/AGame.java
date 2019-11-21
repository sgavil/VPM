package com.gavilanvillar.android_engine;

import android.graphics.Point;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.engine.Audio;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.GameState;
import com.gavilanvillar.engine.GameStateManager;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;
import com.gavilanvillar.engine.ResourceManager;

/**
 * Clase AGame
 *
 * Clase que implementa el bucle principal del juego
 */
public class AGame implements Game, Runnable{

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Constructora y métodos de inicialización (de AGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public AGame(){
    } // AGame

    /**
     * Inicializa AGame
     *
     * @param androidEntry Activity principal de la aplicación
     * @param gameState estado actual de la aplicación
     */
    public void init(AppCompatActivity androidEntry, GameState gameState){

        androidEntry.getSupportActionBar().hide();
        androidEntry.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        Point size = new Point();
        androidEntry.getWindowManager().getDefaultDisplay().getSize(size);

        SurfaceView view = new SurfaceView(androidEntry);
        androidEntry.setContentView(view);

        this._graphics = new AGraphics(androidEntry.getAssets(), view, size);
        this._input = new AInput(view);
        this._audio = new AAudio(androidEntry.getAssets(),androidEntry);

        this._gameStateManager = new GameStateManager();
        this._gameStateManager.setState(gameState);

    } // init

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            if(!_audio.isSoundMuted())
                 _audio.unMuteAll();
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _gameThread = new Thread(this);
            _gameThread.start();
        } // if (!_running)

    } // resume

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            _audio.muteAll();
            while (true) {
                try {
                    _gameThread.join();
                    _gameThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //           Métodos reimplementados (de Runnable)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método que implementa el bucle principal del "juego" y que será
     * ejecutado en otra hebra. Aunque sea público, NO debe ser llamado
     * desde el exterior.
     */
    @Override
    public void run() {

        if (_gameThread != Thread.currentThread()) {
            // ¿¿Quién es el tuercebotas que está llamando al
            // run() directamente?? Programación defensiva
            // otra vez, con excepción, por merluzo.
            throw new RuntimeException("run() should not be called directly");
        }

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle principal.
        while(_running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            GameState actualState = _gameStateManager.getActualState();

            actualState.update(elapsedTime);
            actualState.handleEvent();

            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            _graphics.lockCanvas();
            actualState.render();
            _graphics.unlockCanvas();

        }

    } // run

    /**
     * @return Manager de Estados
     */
    @Override
    public GameStateManager getGameStateManager(){

        return _gameStateManager;

    } // getGameStateManager

    /**
     * @return Graphics
     */
    @Override
    public Graphics getGraphics() {

        return _graphics;

    } // getGraphics

    /**
     * @return Input
     */
    @Override
    public Input getInput() {

        return _input;

    } // getInput

    @Override
    public Audio getAudio() {
        return _audio;
    }

    @Override
    public void setFullscreen(boolean b) {

    }

    @Override
    public void release() {
        _audio.releaseAll();
    }

    @Override
    public void setRunning(boolean b) {
        _running = b;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Objeto Thread que está ejecutando el método run() en una hebra
     * diferente. Cuando se pide a la vista que se detenga el active
     * rendering, se espera a que la hebra termine.
     */
    Thread _gameThread;

    /**
     * Bandera que indica si está o no en marcha la hebra de
     * active rendering, y que se utiliza para sincronización.
     * Es importante que el campo sea volatile.
     *
     * Java proporciona un mecanismo integrado para solicitar la
     * detencción de una hebra, aunque por simplicidad nosotros
     * motamos el nuestro propio.
     */
    volatile boolean _running = false;

    private static AGraphics _graphics = null;
    private static AInput _input = null;
    private static AAudio _audio = null;
    private static GameStateManager _gameStateManager = null;

} // class AGame
