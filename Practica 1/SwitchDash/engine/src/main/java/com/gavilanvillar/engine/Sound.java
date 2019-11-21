package com.gavilanvillar.engine;

/**
 * Interfaz que contiene los métodos necesarios para poder gestionar la reproducción de un sonido
 */
public interface Sound{
    /**
     * Empieza a reproducir la pista
     */
    void play();

    /**
     * Libera los recursos asociados a la pista
     */
    void release();

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
