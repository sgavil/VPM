package com.gavilanvillar.engine;

/**
 * Interfaz Game
 *
 * Mantiene las instancias de Graphics, Input y GameStateManager.
 */
public interface Game {

    /**
     * Devuelve la instancia del gestor de estados.
     *
     * @return GameStateManager
     */
    GameStateManager getGameStateManager();

    /**
     * Devuelve la instancia del "motor" gráfico.
     *
     * @return  Graphics
     */
    Graphics getGraphics();

    /**
     * Devuelve la entrada del gestor de salida.
     *
     * @return  Input
     */
    Input getInput();

    /**
     * Devuelve la instancia del gestor de audio.
     *
     * @return Audio
     */
    Audio getAudio();

    void setRunning(boolean b);
}
