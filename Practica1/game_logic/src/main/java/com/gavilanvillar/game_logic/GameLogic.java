package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Logic;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Hashtable;

public class GameLogic implements Logic {
    public GameLogic(Game game){
        this._game = game;
    }

    public void init(){
        _resources = new Hashtable();
        loadResources();
    }

    /**
     * Método que carga todos los recursos gráficos que encuentra en la carpeta "assets/sprites/"
     */
    void loadResources() {

        _resources.put("ArrowsBackground", _game.getGraphics().newImage("sprites/arrowsBackground.png"));
        _resources.put("Backgrounds", _game.getGraphics().newImage("sprites/backgrounds.png"));
        _resources.put("Balls", _game.getGraphics().newImage("sprites/balls.png"));
        _resources.put("Buttons", _game.getGraphics().newImage("sprites/buttons.png"));
        _resources.put("GameOver", _game.getGraphics().newImage("sprites/gameOver.png"));
        _resources.put("HowToPlay", _game.getGraphics().newImage("sprites/howToPlay.png"));
        _resources.put("Instructions", _game.getGraphics().newImage("sprites/instructions.png"));
        _resources.put("PlayAgain", _game.getGraphics().newImage("sprites/playAgain.png"));
        _resources.put("Players", _game.getGraphics().newImage("sprites/players.png"));
        _resources.put("ScoreFonts", _game.getGraphics().newImage("sprites/scoreFonts.png"));
        _resources.put("SwitchDashLogo", _game.getGraphics().newImage("sprites/switchDashLogo.png"));
        _resources.put("TapToPlay", _game.getGraphics().newImage("sprites/tapToPlay.png"));
        _resources.put("White", _game.getGraphics().newImage("sprites/white.png"));

    }

    public void start(){}

    Game _game;
    Dictionary _resources;

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(double deltaTime) {
        _game.getGraphics().drawImage((Image)_resources.get("Buttons"), 5, 0,
                100,0,
                10,1);

        _game.getGraphics().drawImage((Image)_resources.get("HowToPlay"),0,300);
    }
}
