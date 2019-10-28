package com.gavilanvillar.android_engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
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
        _runnable = new MyRunnable();
        _view = new MySurfaceView(_androidEntry);
        _androidEntry.setContentView(_view);

        _graphics = new AGraphics(_androidEntry.getAssets(), _view);
        _input = new AInput();
        _gameLogic = gameLogic;
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
    private static AppCompatActivity _androidEntry = null;
    private static GameLogic _gameLogic = null;

    private static MySurfaceView _view = null;
    private static MyRunnable _runnable = null;

    //--------------------------------------------------------------------
    //                         Clase MySurfaceView
    //--------------------------------------------------------------------

    /**
     * Clase con la vista principal de la actividad, que se incluye en ella
     * ocupando todo el espacio disponible.
     *
     * Implementa también el interfaz Runnable para proporcionar
     * active rendering. Para ello es necesario lanzar la ejecución de una
     * hebra cuyo ciclo de vida se gestionará aquí. Para eso, se proporcionan
     * métodos que deben ser llamados externamente para notificar cambios
     * en el ciclo de vida de la actividad.
     *
     * En condiciones normales (una aplicación más compleja) estas dos
     * funcionalidades estarían separadas.
     */
    class MySurfaceView extends SurfaceView {

        /**
         * Constructor.
         *
         * @param context Contexto en el que se integrará la vista
         *                (normalmente una actividad).
         */
        public MySurfaceView(Context context) {

            super(context);
            _holder = getHolder();

        } // MySurfaceView

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        /**
         * Realiza la actualización de "la lógica" de la aplicación. En particular,
         * desplaza la imagen a su nueva posición en su deambular de izquierda
         * a derecha.
         *
         * @param deltaTime Tiempo transcurrido (en segundos) desde la invocación
         * anterior (frame anterior).
         */
        protected void update(double deltaTime) {

        } // update

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        /**
         * Dibuja en pantalla el estado actual de la aplicación.
         *
         * @param c Objeto usado para enviar los comandos de dibujado.
         */
        protected void render(Canvas c) {
            _gameLogic.render();
        } // render

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //        Atributos protegidos/privados (de MySurfaceView)
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        /**
         * Manejador de la superficie para poder acceder a su contenido.
         */
        SurfaceHolder _holder;

    } // class MySurfaceView


    //--------------------------------------------------------------------
    //                         Clase MyRunnable
    //--------------------------------------------------------------------
    class MyRunnable implements Runnable{
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        /**
         * Método llamado para solicitar que se continue con el
         * active rendering. El "juego" se vuelve a poner en marcha
         * (o se pone en marcha por primera vez).
         */
        public void resume() {

            if (!_running) {
                // Solo hacemos algo si no nos estábamos ejecutando ya
                // (programación defensiva, nunca se sabe quién va a
                // usarnos...)
                _running = true;
                // Lanzamos la ejecución de nuestro método run()
                // en una hebra nueva.
                _renderThread = new Thread(this);
                _renderThread.start();
            } // if (!_running)

        } // resume

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        /**
         * Método llamado cuando el active rendering debe ser detenido.
         * Puede tardar un pequeño instante en volver, porque espera a que
         * se termine de generar el frame en curso.
         *
         * Se hace así intencionadamente, para bloquear la hebra de UI
         * temporalmente y evitar potenciales situaciones de carrera (como
         * por ejemplo que Android llame a resume() antes de que el último
         * frame haya terminado de generarse).
         */
        public void pause() {

            if (_running) {
                _running = false;
                while (true) {
                    try {
                        _renderThread.join();
                        _renderThread = null;
                        break;
                    } catch (InterruptedException ie) {
                        // Esto no debería ocurrir nunca...
                    }
                } // while(true)
            } // if (_running)

        } // pause

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        /**
         * Método que implementa el bucle principal del "juego" y que será
         * ejecutado en otra hebra. Aunque sea público, NO debe ser llamado
         * desde el exterior.
         */
        @Override
        public void run() {

            if (_renderThread != Thread.currentThread()) {
                // ¿¿Quién es el tuercebotas que está llamando al
                // run() directamente?? Programación defensiva
                // otra vez, con excepción, por merluzo.
                throw new RuntimeException("run() should not be called directly");
            }

            long lastFrameTime = System.nanoTime();

            long informePrevio = lastFrameTime; // Informes de FPS
            int frames = 0;

            // Bucle principal.
            while(_running) {

                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                double elapsedTime = (double) nanoElapsedTime / 1.0E9;
                _view.update(elapsedTime);
                // Informe de FPS
                if (currentTime - informePrevio > 1000000000l) {
                    long fps = frames * 1000000000l / (currentTime - informePrevio);
                    System.out.println("" + fps + " fps");
                    frames = 0;
                    informePrevio = currentTime;
                }
                ++frames;

                // Pintamos el frame
                /*while (!_view._holder.getSurface().isValid())
                    ;
                Canvas canvas = _view._holder.lockCanvas();
                _view.render(canvas);
                _view._holder.unlockCanvasAndPost(canvas);*/

                _graphics.lockCanvas();
                _gameLogic.render();
                _graphics.unlockCanvas();
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/

            } // while

        } // run

        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //        Atributos protegidos/privados (de AGame)
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        /**
         * Objeto Thread que está ejecutando el método run() en una hebra
         * diferente. Cuando se pide a la vista que se detenga el active
         * rendering, se espera a que la hebra termine.
         */
        Thread _renderThread;

        /**
         * Bandera que indica si está o no en marcha la hebra de
         * active rendering, y que se utiliza para sincronización.
         * Es importante que el campo sea volatile.
         *
         * Java proporciona un mecanismo integrado para solicitar la
         * detencción de una hebra, aunque por simplicidad nosotros
         * motamos el nuestro propio.
         */
        volatile boolean _running = false;
    } // class MyRunnable
}
