package com.gavilanvillar.engine;

/**
 * Interfaz GameState
 *
 * Representa un estado de juego.
 */

public interface GameState {
    /**
     * Realiza las actualizaciones lógicas del estado
     * @param deltaTime Tiempo en milisegundos desde que se ejecutó el frame anterior
     */
    void update(double deltaTime);

    /**
     * Realiza las actualizaciones gráficas
     */
    void render ();

    /**
     * Gestiona los eventos de input del usuario
     */
    void handleEvent();
}
