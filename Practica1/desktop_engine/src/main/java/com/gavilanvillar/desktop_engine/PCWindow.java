package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * Clase PCWindow
 *
 * Inicializa la ventana de JFrame y obtiene el bufferStrategy
 */
public class PCWindow extends JFrame {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //      Métodos de inicialización (de PCWindow)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public PCWindow(String title) {
        super(title);
    }

    //Devuelve false si no se ha iniciado correctamente
    public boolean init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIgnoreRepaint(true);

        getContentPane().setFocusable(true);
        setVisible(true);

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return false;
        } else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        // Obtenemos el Buffer Strategy que se supone acaba de crearse.
        _strategy = getBufferStrategy();

        return true;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //      Getters y Setters (de PCWindow)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public BufferStrategy getStrategy() {
        return _strategy;
    }

    public void setGraphics(Graphics g) {
        this._graphics = g;
        this.addComponentListener((PCGraphics) g);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Atributos privados (de PCWindow)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private BufferStrategy _strategy = null;
    private Graphics _graphics = null;

    private final int WINDOW_WIDTH = 1080;
    private final int WINDOW_HEIGHT = 1920;
}
