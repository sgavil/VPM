package com.gavilanvillar.android_game;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.android_engine.AGame;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.game_logic.Menu;


/**
 *
 * Punto de entrada de android, Activity principal de la aplicación
 *
 * Gestiona la inicialización del estado inicial, la creación del gestor de recursos y la creación del Game.
 */
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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        _game.getGraphics().swapPhysicResolution();

    } // onConfigurationChanged


    /**
     * Método que inicializa el juego, el estado inicial y el manager de recursos
     */
    void init(){

        // Inicializamos AGame
        _game = new AGame();

        // Inicializamos GameLogic
        _menuState = new Menu(_game);

        _game.init(this, _menuState);

        ResourceManager _resourceManager = new ResourceManager(_game);
        _resourceManager.init();

        //setVolumeControlStream(AudioManager.STREAM_MUSIC);

        _menuState.init(_resourceManager);



    } // init

    private AGame _game = null;
    private Menu _menuState = null;
}
