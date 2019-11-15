package com.gavilanvillar.desktop_engine;
import com.gavilanvillar.engine.Sound;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class PCSound implements Sound {

    public PCSound(Clip clip){
        _clip= clip;
        _clipVolume = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
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
        _clipVolume.setValue(0.0f);
    }

    @Override
    public void unMute() {
        _clipVolume.setValue(1.0f);
    }

    @Override
    public void stop() {

    }

    private Clip _clip;
    private FloatControl _clipVolume;
}
