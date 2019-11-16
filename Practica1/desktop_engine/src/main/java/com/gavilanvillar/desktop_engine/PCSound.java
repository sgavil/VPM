package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Sound;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;


/**
 * Clase que hereda de Sound e implementa los métodos de gestión de un sonido ( reproducción, pausa...)
 */
public class PCSound implements Sound {

    public PCSound(Clip clip) {
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

    //Variable para controlar que la pista esté o no en MUTE
    private BooleanControl _bc;

}
