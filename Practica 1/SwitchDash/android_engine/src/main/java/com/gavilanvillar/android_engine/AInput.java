package com.gavilanvillar.android_engine;

import android.view.MotionEvent;
import android.view.View;

import com.gavilanvillar.engine.AbstractInput;

import java.util.ArrayList;

/**
 * Clase que Hereda de AbstractInput y se encarga de transformar eventos de Android en
 * TouchEvents y añadirlos a la lista de eventos
 */
public class AInput extends AbstractInput {

    public AInput(View view) {

        _touchEventList = new ArrayList<>();

        //Se añade un listener a la vista
        view.setOnTouchListener(new View.OnTouchListener() {
            //Metodo que se llamara automaticamente al generarse un evento
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TouchEvent event = new TouchEvent();

                final int actionPermorfed = motionEvent.getAction();

                event._x = (int) motionEvent.getX();
                event._y = (int) motionEvent.getY();

                event._id = motionEvent.getActionIndex();

                //Se transforma la accion en un touchEvent
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        event._type = EventType.PULSADO;
                        break;
                    case MotionEvent.ACTION_UP:
                        event._type = EventType.LIBERADO;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        event._type = EventType.DESPLAZADO;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        event._type = EventType.PULSADO;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        event._type = EventType.LIBERADO;
                        break;
                    default:
                        break;
                }

                addEvent(event);
                return true;
            }

        });
    }
}
