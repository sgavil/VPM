package com.gavilanvillar.practica1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.gavilanvillar.android_tech.AGame;
import com.gavilanvillar.android_tech.AGraphics;
import com.gavilanvillar.android_tech.AInput;
import com.gavilanvillar.logic.GameLogic;

public class android_entry extends AppCompatActivity {

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
