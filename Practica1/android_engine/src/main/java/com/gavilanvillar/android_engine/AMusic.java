package com.gavilanvillar.android_engine;

import android.media.MediaPlayer;

import com.gavilanvillar.engine.Music;

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
        _mediaPlayer.stop();
    }

    @Override
    public void unMute() {
        _mediaPlayer.setVolume(1.0f, 1.0f);
    }

    @Override
    public void stop() {
        _mediaPlayer.stop();
    }

    private MediaPlayer _mediaPlayer;

}
