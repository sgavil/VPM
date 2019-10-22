package com.gavilanvillar.java_entry;

import com.gavilanvillar.java_tech.JGame;
import com.gavilanvillar.java_tech.JGraphics;
import com.gavilanvillar.java_tech.JInput;
import com.gavilanvillar.logic.GameLogic;

public class java_entry {
    public static void main(String[]args){

        JInput input = new JInput();
        JGraphics graphics = new JGraphics();
        JGame game = new JGame(graphics, input);

        new GameLogic(game);

    }
}
