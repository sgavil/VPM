package com.gavilanvillar.engine;

import java.util.ArrayList;
import java.util.List;


public interface Input {
    public enum EventType
    {
        PULSADO,
        LIBERADO,
        DESPLAZADO
    }

    class TouchEvent{
        public TouchEvent(){
        }

        public int _x;
        public int _y;

        public EventType _type;
    }

    /**
     * Devuelve la lista de eventos recibidos desde la última
     * invocación al método.
     *
     * @return lista de eventos
     */
    List<TouchEvent> getTouchEvents();


}
