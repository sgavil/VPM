package com.gavilanvillar.desktop_entry;

import com.gavilanvillar.desktop_engine.PCGame;
import com.gavilanvillar.desktop_engine.PCWindow;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.game_logic.Menu;


public class DesktopEntry {
    public static void main(String[]args){

        PCWindow window = new PCWindow("SwitchDash");
        if(!window.init())
            return;

        PCGame game = new PCGame();
        Menu gameLogic = new Menu(game);

        game.init(window, gameLogic);

        ResourceManager resourceManager = new ResourceManager(game);
        resourceManager.init();

        gameLogic.init(resourceManager);

        game.run();
    }
}
