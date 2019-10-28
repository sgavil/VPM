package com.gavilanvillar.android_game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.android_engine.AGame;
import com.gavilanvillar.game_logic.GameLogic;


public class AndroidEntry extends AppCompatActivity {

    /**
     * Método llamado por Android como parte del ciclo de vida de
     * la actividad. Se llama en el momento de lanzarla.
     *
     * @param savedInstanceState Información de estado de la actividad
     *                           previamente serializada por ella misma
     *                           para reconstruirse en el mismo estado
     *                           tras un reinicio. Será null la primera
     *                           vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();

    } // onCreate

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     *
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume(){

        super.onResume();
        _game.resume();

    } // onResume

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause(){

        super.onPause();
        _game.pause();

    } // onPause


    /**
     * Método que inicializa el juego y la lógica. Por último llama al
     * bucle del juego, el bucle principal.
     */
    void init(){

        // Inicializamos AGame
        _game = new AGame();

        // Inicializamos GameLogic
        _gameLogic = new GameLogic(_game);

        _game.init(this, _gameLogic);
        _gameLogic.init();

        //_game.run();

    } // init

    AGame _game = null;
    GameLogic _gameLogic = null;
}
