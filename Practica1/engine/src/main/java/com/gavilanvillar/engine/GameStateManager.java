package com.gavilanvillar.engine;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    public GameStateManager(){
    }

    public void setState(GameState g){
        _actualGameState = g;
    }

    public GameState getActualState() {
        return _actualGameState;
    }

    GameState _actualGameState = null;

}
