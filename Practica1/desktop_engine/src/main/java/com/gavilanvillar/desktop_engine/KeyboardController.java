package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements KeyListener {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos de inicialización (de KeyboardController)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public KeyboardController(){

    }

    public void init(PCInput input){
        this._input = input;
    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Eventos de teclado (de KeyboardListener)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        TouchEvent event = new TouchEvent();
        event._type = EventType.PULSADO;

        event._id = ke.getKeyCode();

        _input.addEvent(event);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        TouchEvent event = new TouchEvent();
        event._type = EventType.LIBERADO;

        event._id = ke.getKeyCode();

        _input.addEvent(event);
    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Atributos privados/protegidos (de KeyboardController)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private PCInput _input = null;

}
