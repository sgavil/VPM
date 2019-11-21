package com.gavilanvillar.engine;

/**
 * Interfaz que contiene los métodos necesarios para gestionar un archivo de música
 *
 */
public interface Music {

    /**
     * Empieza a reproducir la pista
     */
    void play();

    /**
     * Libera los recursos asociados a la pista
     */
    void release();

    /**
     * Permite reproducir la pista en bucle
     * @param loopActive indica si se quiere o no reproducir en bucle la pista
     */
    void setLoop(boolean loopActive);

    /**
     * Silencia la pista
     */
    void mute();

    /**
     * Devuelve el sonido a la pista
     */
    void unMute();

    /**
     * Para la reproducción de la pista
     */
    void stop();

    /**
     * Devuelve la reproducción de la pista al punto donde se paró
     */
    void resume();
}
