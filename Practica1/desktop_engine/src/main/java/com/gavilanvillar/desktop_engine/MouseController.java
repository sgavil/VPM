package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {

    // MouseListener
    public void mouseClicked(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.PULSADO;
    }

    public void mouseReleased(MouseEvent e){
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.PULSADO;
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
    }

    public void mouseMoved(MouseEvent e){

    }

}
