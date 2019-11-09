package com.gavilanvillar.desktop_entry;

import com.gavilanvillar.desktop_engine.PCGame;
import com.gavilanvillar.desktop_engine.PCWindow;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.game_logic.Menu;

/**
 *
 * Punto de entrada de la plataforma PC
 *
 * Llama a la creación de la ventana, crea el Game, el estado inicial e inicializa
 * el gestor de recursos
 */
public class DesktopEntry {
    public static void main(String[]args){

        PCWindow window = new PCWindow("SwitchDash");

        //Si no se ha inicializado bien la ventana no ejecutamos la aplicación
        if(!window.init())
            return;

        PCGame game = new PCGame();
        Menu gameLogic = new Menu(game);

        game.init(window, gameLogic);

        //Creación e inicialización del manager de recursos
        ResourceManager resourceManager = new ResourceManager(game);
        resourceManager.init();

        gameLogic.init(resourceManager);

        game.run();
    }
}
