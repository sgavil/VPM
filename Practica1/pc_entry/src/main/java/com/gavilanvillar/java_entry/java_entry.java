package com.gavilanvillar.java_entry;

import com.gavilanvillar.pc_tech.PCGame;
import com.gavilanvillar.pc_tech.PCGraphics;
import com.gavilanvillar.pc_tech.PCInput;
import com.gavilanvillar.logic.GameLogic;

public class java_entry {
    public static void main(String[]args){

        PCInput input = new PCInput();
        PCGraphics graphics = new PCGraphics();
        PCGame game = new PCGame(graphics, input);

        new GameLogic(game);

    }
}
