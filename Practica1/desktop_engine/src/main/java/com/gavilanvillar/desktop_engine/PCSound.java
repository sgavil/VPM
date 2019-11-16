package com.gavilanvillar.desktop_engine;
import com.gavilanvillar.engine.Sound;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class PCSound implements Sound {

    public PCSound(Clip clip){
        _clip= clip;
        _clipVolume = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
        _bc = (BooleanControl) _clip.getControl(BooleanControl.Type.MUTE);

    }
    @Override
    public void play() {
        _clip.setMicrosecondPosition(0);
        _clip.start();
    }

    @Override
    public void release() {

    }

    @Override
    public void setLoop(boolean loopActive) {

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
    private FloatControl _clipVolume;
    private BooleanControl _bc;

}
