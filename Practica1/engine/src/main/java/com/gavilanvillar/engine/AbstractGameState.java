package com.gavilanvillar.engine;

public abstract class AbstractGameState implements GameState {
    protected final int WIDTH_RES = 1080;
    protected final int HEIGHT_RES = 1920;

    public AbstractGameState(Game game){
        this._game = game;
    }

    public void init(ResourceManager resourceManager){
        this._resourceManager = resourceManager;

        ((AbstractGraphics) _game.getGraphics()).setLogicResolution(WIDTH_RES, HEIGHT_RES);
    }

    protected Game _game;
    protected ResourceManager _resourceManager;
}
