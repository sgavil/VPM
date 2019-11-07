package com.gavilanvillar.engine;

public interface GameState {
    void update(double deltaTime);
    void render ();
    void handleEvent();
}
