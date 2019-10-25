package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Image;

import java.util.Dictionary;
import java.util.Hashtable;

public class GameLogic {
    public GameLogic(){
    }

    public void init(Game game){

        this._game = game;
        _resources = new Hashtable();

        loadResources();
    }

    void loadResources() {

        _resources.put("Balls", this._game.getGraphics().newImage("sprites/balls.png"));
        _resources.put("Background", this._game.getGraphics().newImage("sprites/background.png"));
    }

    public void start(){}

    public void render(){
        _game.getGraphics().drawImage((Image)_resources.get("Balls"), 40, 40);
    }

    public void update(){

    }

    Game _game;
    Dictionary _resources;
}
