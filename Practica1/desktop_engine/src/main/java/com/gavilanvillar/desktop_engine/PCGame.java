package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;

public class PCGame implements Game {
    public PCGame(PCGraphics graphics, PCInput input){
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

    @Override
    public void run() {

    }

    private static PCGraphics _graphics = null;
    private static PCInput _input = null;
}
