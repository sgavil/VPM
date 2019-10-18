package com.gavilanvillar.android_tech;

import android.content.res.AssetManager;

import com.gavilanvillar.abs_layer.Game;
import com.gavilanvillar.abs_layer.Graphics;
import com.gavilanvillar.abs_layer.Input;

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
