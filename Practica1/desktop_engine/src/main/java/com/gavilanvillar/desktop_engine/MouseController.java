package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {

    public void MouseController(){

    }

    public void init(PCInput input){
        this._input = input;
    }
    // MouseListener
    public void mouseClicked(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.PULSADO;

        _input.addEvent(event);
    }

    public void mouseReleased(MouseEvent e){
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.LIBERADO;

        _input.addEvent(event);
    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }


    //MouseMotionListener
    public void mouseDragged(MouseEvent e){
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.DESPLAZADO;

        _input.addEvent(event);
    }

    public void mouseMoved(MouseEvent e){

    }

    PCInput _input = null;
}
