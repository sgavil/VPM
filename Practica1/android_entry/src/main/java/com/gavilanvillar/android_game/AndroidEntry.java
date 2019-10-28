package com.gavilanvillar.android_game;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.android_engine.AGame;
import com.gavilanvillar.game_logic.GameLogic;

public class AndroidEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init(){
        System.out.print("HOLAAA");
        // Inicializamos AGame
        _game = new AGame(this);
        //Se llama a la logica
        _gameLogic = new GameLogic(_game);
        _game.init(_gameLogic);
        _gameLogic.init();

        _game.run();
    }

    @Override
    protected void onResume(){
        super.onResume();
        _game.resume();
    }

    protected void onPause(){
        super.onPause();
        _game.pause();
    }


    AGame _game = null;
    GameLogic _gameLogic = null;
}
