package com.gavilanvillar.pc_tech;

import com.gavilanvillar.abs_layer.Game;
import com.gavilanvillar.abs_layer.Graphics;
import com.gavilanvillar.abs_layer.Input;

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

    private static PCGraphics _graphics = null;
    private static PCInput _input = null;
}
