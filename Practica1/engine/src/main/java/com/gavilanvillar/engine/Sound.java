package com.gavilanvillar.engine;

public interface Sound{
    void play();

    void release();

    void setLoop(boolean loopActive);

    void mute();

    void unMute();

    void stop();

    void resume();
}
