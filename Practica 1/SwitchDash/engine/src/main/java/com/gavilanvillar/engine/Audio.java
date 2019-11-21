package com.gavilanvillar.engine;


/**
 * Interfaz encargada de gestionar la creacion de sonidos y música del juego además de controlar si el sonido/música
 * está muteado/no muteado. Cuando se acaba la ejecución libera los recursos utilizados
 */
public interface Audio {

    /**
     * Carga un nuevo Sonido Sound
     * @param name Ruta del archivo que se va a cargar
     * @return archivo de tipo Sound
     */
    Sound newSound(String name);


    /**
     * Silencia toda la música y sonidos que se hayan cargado en el juego
     */
    void muteAll();

    /**
     * Devuelve el volumen al sonido y la música que se haya cargado en el juego
     */
    void unMuteAll();

    /**
     * Carga una nueva música y la devuelve
     * @param name Ruta de la pista que se va a cargar
     * @return Objeto de tipo Music
     */
    Music newMusic(String name);


    /**
     * Para la reproducción de todos los sonidos que se hayan cargado
     */
    void stopAll();

    /**
     * Vuelve a reproducir todos los sonidos que se hayan cargado
     */
    void resumeAll();

    /**
     * Comprueba si el sonido se encuentra muteado o no
     * @return True si el sonido está muteado , false en caso contrario
     */
    boolean isSoundMuted();

    /**
     * Permite cambiar el valor actual del sonido de muteado a no muteado y viceversa
     * @param isSoundMuted
     */
    void setSoundState(boolean isSoundMuted);

    /**
     * Libera todos los recursos de sonido y música que se hayan creado al cargar los sonidos y la música
     */
    void releaseAll();
}
