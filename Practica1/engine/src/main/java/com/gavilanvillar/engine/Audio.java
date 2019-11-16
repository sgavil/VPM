package com.gavilanvillar.engine;

public interface Audio {

    Sound newSound(String name);

    void muteAll();

    void unMuteAll();

    Music newMusic(String name);

    void stopAll();

    void resumeAll();

    boolean isSoundMuted();

    void setSoundState(boolean isSoundMuted);
}
