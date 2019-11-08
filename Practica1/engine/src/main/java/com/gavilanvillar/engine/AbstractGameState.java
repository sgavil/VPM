package com.gavilanvillar.engine;

public abstract class AbstractGameState implements GameState {
    protected final int WIDTH_RES = 1080;
    protected final int HEIGHT_RES = 1920;

    public AbstractGameState(Game game){
        this._game = game;
    }


    public void init(ResourceManager resourceManager){
        this._resourceManager = resourceManager;

        _game.getGraphics().setLogicResolution(WIDTH_RES, HEIGHT_RES);

        _arrowsBackground = _resourceManager.getArrowsBackground();
        _arrowsPosY_0 = 0 - _arrowsBackground.getImage().getHeight();
        _arrowsPosY_1 = 0;

        _tapToPlay = _resourceManager.getTapToPlay();
    }

    protected void arrowsMovement(double deltaTime){
        int newArrowsPos = 0;
        newArrowsPos += (_arrowsVel * deltaTime);
        _arrowsPosY_0 += newArrowsPos;
        _arrowsPosY_1 += newArrowsPos;

        if(_arrowsPosY_0 >= HEIGHT_RES && _arrowsPosY_1 >= 0)
            _arrowsPosY_0 = _arrowsPosY_1 - _arrowsBackground.getImage().getHeight();
        if(_arrowsPosY_1 >= HEIGHT_RES && _arrowsPosY_0 >= 0)
            _arrowsPosY_1 = _arrowsPosY_0 - _arrowsBackground.getImage().getHeight();
    }

    protected void fadeInFadeOutTap(double deltaTime){
        if(tapAlpha +(deltaAlpha * deltaTime) >= 1.0f) {
            fadeOut = true;
            fadeIn = false;

        }
        else if (tapAlpha - (deltaAlpha * deltaTime) <= 0.0f) {
            fadeOut = false;
            fadeIn = true;
        }


        if(fadeOut)
            tapAlpha -= (deltaAlpha * deltaTime);
        else if (fadeIn)
            tapAlpha += (deltaAlpha * deltaTime);
    }

    protected Game _game;
    protected ResourceManager _resourceManager;

    private float _arrowsVel = 384f;
    protected int _arrowsPosY_0 = 0;
    protected int _arrowsPosY_1 = 0;

    private float deltaAlpha = 1.2f;
    protected float tapAlpha = 1.0f;
    private boolean fadeIn = false;
    private boolean fadeOut = true;

    protected Sprite _actualBackground = null;
    protected Sprite _arrowsBackground = null;

    protected Sprite _tapToPlay = null;
}
