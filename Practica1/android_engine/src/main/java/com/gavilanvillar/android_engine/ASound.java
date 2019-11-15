package com.gavilanvillar.android_engine;
import android.media.SoundPool;

import com.gavilanvillar.engine.Sound;

public class ASound implements Sound {

    public ASound(int _soundID, SoundPool pool) {
        this._soundID = _soundID;
        this._pool = pool;
    }

    @Override
    public void play() {
        if(_soundID != -1){
            _pool.play(_soundID, _leftVolume, _rightVolume, 0, _loop, 1);
        }
        else{
            System.out.print("No se ha podido reproducir el sonido");
        }
    }

    @Override
    public void release() {
        _pool.unload(_soundID);
    }

    @Override
    public void setLoop(boolean loopActive) {
        _loop = loopActive ? 1 : 0;
    }

    @Override
    public void mute() {
        _leftVolume = 0f;
        _rightVolume = 0f;
        _pool.stop(_soundID);
    }

    @Override
    public void unMute() {
        _leftVolume = 1.0f;
        _rightVolume = 1.0f;

    }

    @Override
    public void stop() {
        _pool.stop(_soundID);
    }

    @Override
    public void resume(){
        _pool.resume(_soundID);
    }
    private int _soundID;
    private SoundPool _pool;
    private int _loop;
    private float _leftVolume = 1.0f;
    private float _rightVolume = 1.0f;
}
