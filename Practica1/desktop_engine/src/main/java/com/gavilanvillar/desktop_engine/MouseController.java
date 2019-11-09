package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * Clase MouseController
 * <p>
 * Implementa las interfaces MouseListener y MouseMotionListener para recibir
 * los eventos de ratón como pulsaciónes...
 * <p>
 * En los eventos se devuelve un ID indicando el botón que ha sido pulsado.
 * <p>
 * El id del boton corresponde a lo siguiente:
 * 0 - Ningun botón
 * 1 - Boton Izquierdo
 * 2 - Boton Derecho
 * 3 - Boton Central
 */
public class MouseController implements MouseListener, MouseMotionListener {


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos de inicialización (de MouseController)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public MouseController() {

    }

    public void init(PCInput input) {
        this._input = input;
    }
    // MouseListener


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Eventos de ratón (de MouseListener y MouseMotionListener)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Metodo que es llamado cuando se hace un "clic" de ratón.
     *
     * @param e Evento generado
     */
    public void mouseClicked(MouseEvent e) {
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.PULSADO;

        event._id = e.getButton();

        _input.addEvent(event);
    }


    public void mousePressed(MouseEvent e) {

    }

    /**
     * Metodo que es llamado cuando se suelta el raton
     *
     * @param e Evento generado
     */
    public void mouseReleased(MouseEvent e) {
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.LIBERADO;

        event._id = e.getButton();

        _input.addEvent(event);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }


    /**
     * Metodo que es llamado cuando se arrastra el ratón
     *
     * @param e Evento generado
     */
    public void mouseDragged(MouseEvent e) {
        TouchEvent event = new TouchEvent();
        event._x = e.getX();
        event._y = e.getY();
        event._type = EventType.DESPLAZADO;

        event._id = e.getButton();

        _input.addEvent(event);
    }

    public void mouseMoved(MouseEvent e) {

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos privados/protegidos (de MouseController)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private PCInput _input = null;
}
