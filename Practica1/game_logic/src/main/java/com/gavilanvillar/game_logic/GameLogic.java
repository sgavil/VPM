package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Logic;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.Sprite;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Hashtable;

public class GameLogic implements Logic {
    final int ANCHO_RES = 1080;
    final int ALTO_RES = 1920;

    public GameLogic(Game game){
        this._game = game;
    }

    public void init(){
        ((AbstractGraphics)_game.getGraphics()).setLogicResolution(ANCHO_RES, ALTO_RES);
        loadResources();
    }

    /**
     * Método que carga todos los recursos gráficos que encuentra en la carpeta "assets/sprites/"
     */
    void loadResources() {

        Image image = null;

        image = _game.getGraphics().newImage("sprites/arrowsBackground.png");
        _arrowsBackground = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/backgrounds.png");
        _backgrounds = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));
        _actualBG = new Sprite(image, new Rect(0, image.getHeight(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/balls.png");
        _balls = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/buttons.png");
        _buttons = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/gameOver.png");
        _gameOver = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/howToPlay.png");
        _howToPlay = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/instructions.png");
        _instructions = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/playAgain.png");
        _playAgain = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/players.png");
        _players = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));
        _actualPlayer = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight() / 2));

        image = _game.getGraphics().newImage("sprites/scoreFont.png");
        _scoreFont = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/switchDashLogo.png");
        _switchDashLogo = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/tapToPlay.png");
        _tapToPlay = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/white.png");
       _white = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

    }

    public void orientationChanged(boolean portrait){
        ((AbstractGraphics)_game.getGraphics()).swapPhysicResolution();
    }

    public void start(){

    }


    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(double deltaTime) {
        _actualBG.draw(_game.getGraphics(), _actualBG.getRect(), new Rect(0, ANCHO_RES, 0, ALTO_RES));
        _actualPlayer.drawCentered(_game.getGraphics(), _actualPlayer.getRect(), 1700, 0);
    }


    Game _game;
    Sprite _arrowsBackground = null;

    Sprite _backgrounds = null;
    Sprite _actualBG = null;

    Sprite _balls = null;
    Sprite _buttons = null;
    Sprite _gameOver = null;
    Sprite _howToPlay = null;
    Sprite _instructions = null;
    Sprite _playAgain = null;

    Sprite _players = null;
    Sprite _actualPlayer = null;

    Sprite _scoreFont = null;
    Sprite _switchDashLogo = null;
    Sprite _tapToPlay = null;
    Sprite _white = null;
}
