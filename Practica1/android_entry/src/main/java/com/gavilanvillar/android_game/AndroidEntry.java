package com.gavilanvillar.android_game;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.android_engine.AGame;
import com.gavilanvillar.android_engine.AGraphics;
import com.gavilanvillar.android_engine.AInput;
import com.gavilanvillar.game_logic.GameLogic;

public class AndroidEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init(){
        // Inicializamos AGame
        _game = new AGame(new AGraphics(this.getAssets(), new SurfaceView(this)), new AInput());

        //Se llama a la logica
        _gameLogic = new GameLogic();
        _gameLogic.init(_game);

        _game.run();
    }

    AInput _input;
    AGraphics _graphics;
    AGame _game;
    GameLogic _gameLogic;
}
