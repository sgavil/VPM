package com.gavilanvillar.android_engine;

import android.content.Context;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Input;
import com.gavilanvillar.game_logic.GameLogic;

public class AGame implements Game{

    public AGame(AppCompatActivity androidEntry){
        this._androidEntry = androidEntry;
    }

    public void init(GameLogic gameLogic){
        _view = new MyView(_androidEntry);
        _androidEntry.setContentView(_view);

        _graphics = new AGraphics(_androidEntry.getAssets(), _view);
        _input = new AInput();
        _gameLogic = gameLogic;
    }

    @Override
    public Graphics getGraphics() {
       return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }


    public void resume(){
        _view.resume();
    }

    public void pause(){
        _view.pause();
    }
    @Override
    public void run(){
        _view.run();
    }

    private static AGraphics _graphics = null;
    private static AInput _input = null;
    AppCompatActivity _androidEntry;
    MyView _view;
    GameLogic _gameLogic;

    class MyView extends SurfaceView implements Runnable{
        public MyView (Context context){
            super(context);
        }

        //Ciclo de vida: el bucle principal debe ser puesto en marcha de nuevo
        public void resume(){
            if(!_running) {
                _running = true;
                _renderThread = new Thread(this);
                _renderThread.start();
            }
        }

        //Ciclo de vida: el bucle principal debe ser parado temporalmente
        public void pause(){
            _running = false;
            // Esperar a que termine.
            while(true) {
                try {
                    _renderThread.join();
                    break;
                } catch (InterruptedException e) {

                }
            }
        }   // pause

        // Actualiza la lógica
        public void update(float deltaTime){

        }

        public void render(){
            _graphics.lockCanvas();
            _gameLogic.render();
            _graphics.unlockCanvas();
        }

        public void run(){
            // Cáculo del tiempo que ha pasado, se hace exactamente igual que la version que
            // hicimos para java
            float deltaTime = 0.0f;
            long lastFrameTime = System.nanoTime();

            while(_running){
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                deltaTime = (float) (nanoElapsedTime / 1.0E9);

                //update(deltaTime);

                render();

            } // Bucle principal del juego
        }

        volatile boolean _running = false;
        Thread _renderThread;
    } // class MyView

}
