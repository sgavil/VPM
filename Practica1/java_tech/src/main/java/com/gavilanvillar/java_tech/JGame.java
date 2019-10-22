package com.gavilanvillar.java_tech;

import com.gavilanvillar.abs_layer.Game;
import com.gavilanvillar.abs_layer.Graphics;
import com.gavilanvillar.abs_layer.Input;

public class JGame implements Game {
    public JGame(JGraphics graphics, JInput input){
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

    private static JGraphics _graphics = null;
    private static JInput _input = null;
}
