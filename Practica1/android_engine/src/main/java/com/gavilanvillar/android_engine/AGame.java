package com.gavilanvillar.android_engine;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;
import com.gavilanvillar.game_logic.GameLogic;

public class AGame implements Game{

    public AGame(AGraphics graphics, AInput input){
        this._graphics = graphics;
        this._input = input;
    }

    public void init(GameLogic gameLogic){
        this._gameLogic = gameLogic;
    }

    @Override
    public Graphics getGraphics() {
       return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    private static AGraphics _graphics = null;
    private static AInput _input = null;

    @Override
    public void run(){
        _graphics.lockCanvas();
        _gameLogic.render();
        _graphics.unlockCanvas();
    }

    GameLogic _gameLogic;
}
