package com.gavilanvillar.desktop_entry;

import com.gavilanvillar.desktop_engine.PCGame;
import com.gavilanvillar.desktop_engine.PCWindow;
import com.gavilanvillar.game_logic.SwitchDash;

public class DesktopEntry {
    public static void main(String[]args){

        PCWindow window = new PCWindow("SwitchDash");
        if(!window.init())
            return;

        PCGame game = new PCGame();
        SwitchDash gameLogic = new SwitchDash(game);

        game.init(window, gameLogic);
        gameLogic.init();

        game.run();
    }
}
