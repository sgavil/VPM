package com.gavilanvillar.engine;

/**
 * Clase GameStateManager
 *
 * Instancia de un gestor que controla el estado de juego actual y permite cambiarlo además,
 * permite conocer que estado se está ejecutando en ese momento.
 */
public class GameStateManager {

    public GameStateManager(){
    }

    /**
     * Pasa a ejectuar un nuevo estado
     * @param g Estado que se va a empezar a ejecutar
     */
    public void setState(GameState g){
        _actualGameState = g;
    }

    /**
     * @return Estado que se está ejecutando actualmente
     */
    public GameState getActualState() {
        return _actualGameState;
    }

    private GameState _actualGameState = null;

}
