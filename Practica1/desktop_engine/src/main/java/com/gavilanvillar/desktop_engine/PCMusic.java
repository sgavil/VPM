package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Music;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;

/**
 * Clase que implementa la interfaz Music y sobrescribe los métodos necesarios para la gestión de un archivo de música
 */
public class PCMusic implements Music {

    public PCMusic(Clip clip) {
        _clip = clip;
        _bc = (BooleanControl) _clip.getControl(BooleanControl.Type.MUTE);

    }

    @Override
    public void play() {
        _clip.setMicrosecondPosition(0);
        _clip.start();
    }

    @Override
    public void release() {
        _clip.close();
    }

    @Override
    public void setLoop(boolean loopActive) {
        _clip.loop(loopActive ? 1 : 0);
    }

    @Override
    public void mute() {
        _bc.setValue(true);

    }

    @Override
    public void unMute() {
        _bc.setValue(false);

    }

    @Override
    public void stop() {
        _clip.stop();

    }

    @Override
    public void resume() {
        _clip.start();
    }

    private Clip _clip;

    //Variable para controlar el estado de MUTE de la música
    private BooleanControl _bc;
}
