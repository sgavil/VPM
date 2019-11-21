package com.gavilanvillar.android_engine;

import android.media.MediaPlayer;

import com.gavilanvillar.engine.Music;

/**
 * Clase que implementa la interfaz Music y permite la gestión de una pista de música
 */
public class AMusic implements Music {

    public AMusic(MediaPlayer mp) {
        _mediaPlayer = mp;
    }

    @Override
    public void play() {
        _mediaPlayer.start();

    }

    @Override
    public void release() {
        _mediaPlayer.release();
    }

    @Override
    public void setLoop(boolean loopActive) {
        _mediaPlayer.setLooping(loopActive);
    }

    @Override
    public void mute() {
        _mediaPlayer.setVolume(0.0f, 0.0f);
    }

    @Override
    public void unMute() {

        _mediaPlayer.setVolume(1.0f, 1.0f);
    }

    @Override
    public void resume(){
        _mediaPlayer.start();
    }

    @Override
    public void stop() {
        _mediaPlayer.pause();
    }

    private MediaPlayer _mediaPlayer;


}
