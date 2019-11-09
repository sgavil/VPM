package com.gavilanvillar.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz Input
 *
 * Proporciona las funcionalidades de entrada básicas.
 */
public interface Input {
    /**
     * Distintos tipos de Eventos que se gestionan ( Pulsacion, desplazamiento...)
     */
    enum EventType
    {
        PULSADO,
        LIBERADO,
        DESPLAZADO
    }

    /**
     * Clase que agrupa todos los atributos que representan un evento
     * Posicion en X
     * Posicion en Y
     *
     * Tipo del evento
     *
     * Id del elemento que ha generado el evento ( dedo, boton del raton... )
     */
    class TouchEvent
    {
        public TouchEvent(){
        }

        //Posiciones donde se ha generado el evento
        public int _x;
        public int _y;

        //Tipo del evento
        public EventType _type;

        public int _id;

        //TODO: falta controlar el id del dedo o boton del raton
    }

    /**
     * Devuelve la lista de eventos recibidos desde la última
     * invocación al método.
     *
     * @return lista de eventos
     */
    List<TouchEvent> getTouchEvents();

}
