package com.gavilanvillar.android_engine;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;

public class AGame  implements Game{

    public AGame(AGraphics graphics, AInput input){
        this._graphics = graphics;
        this._input = input;

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
}
