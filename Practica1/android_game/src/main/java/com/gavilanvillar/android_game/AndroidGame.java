package com.gavilanvillar.android_game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.android_engine.AGame;
import com.gavilanvillar.android_engine.AGraphics;
import com.gavilanvillar.android_engine.AInput;
import com.gavilanvillar.game_logic.GameLogic;

public class AndroidGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Se inicializa el motor de android
        AInput input = new AInput();
        AGraphics graphics = new AGraphics(this.getAssets());
        AGame game = new AGame(graphics,input);

        //Se llama a la logica
        new GameLogic(game);
    }
}
