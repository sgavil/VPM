package com.gavilanvillar.engine;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    public GameStateManager(){
    }

    public void nextState(GameState g){
        _actualGameState = g;
    }

    GameState _actualGameState = null;

}
