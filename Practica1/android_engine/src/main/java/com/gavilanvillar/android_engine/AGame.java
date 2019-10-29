package com.gavilanvillar.android_engine;

import android.content.res.Configuration;
import android.graphics.Point;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;
import com.gavilanvillar.engine.Logic;

public class AGame implements Game{

    public AGame(){
    }

    public void init(AppCompatActivity androidEntry, Logic gameLogic){


        androidEntry.getSupportActionBar().hide();

        androidEntry.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape =androidEntry.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        Point size = new Point();
        androidEntry.getWindowManager().getDefaultDisplay().getSize(size);

        SurfaceView view = new SurfaceView(androidEntry);
        androidEntry.setContentView(view);

        _graphics = new AGraphics(androidEntry.getAssets(), view,isLandscape,size);
        _input = new AInput();

        _runnable = new MyRunnable();
        _runnable.init(gameLogic, _graphics);
    }

    public void resume(){
        _runnable.resume();
    }

    public void pause(){
        _runnable.pause();
    }

    @Override
    public Graphics getGraphics() {
       return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AGame)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static AGraphics _graphics = null;
    private static AInput _input = null;
    private static MyRunnable _runnable = null;

} // class AGame
