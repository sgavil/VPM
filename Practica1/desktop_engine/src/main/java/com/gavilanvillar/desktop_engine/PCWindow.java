package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Graphics;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class PCWindow extends JFrame implements ComponentListener {

    public PCWindow(String title){
        super(title);
    }

    public boolean init(){
        setSize(1080,1920);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIgnoreRepaint(true);

        setVisible(true);

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return false;
        }
        else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        // Obtenemos el Buffer Strategy que se supone acaba de crearse.
        _strategy = getBufferStrategy();

        this.addComponentListener(this);

        return true;
    }

    public BufferStrategy getStrategy(){
        return _strategy;
    }

    public void setGraphics(Graphics g){ this._graphics = g; }

    public void componentHidden(ComponentEvent e) {

    }

    public void componentMoved(ComponentEvent e) {

    }

    public void componentResized(ComponentEvent e) {
        ((AbstractGraphics)_graphics).setPhysicResolution(getWidth(), getHeight());
    }

    public void componentShown(ComponentEvent e) {

    }

    private BufferStrategy _strategy = null;
    private Graphics _graphics = null;
}