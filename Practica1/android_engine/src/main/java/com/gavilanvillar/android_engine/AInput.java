package com.gavilanvillar.android_engine;

import android.view.MotionEvent;
import android.view.View;

import com.gavilanvillar.engine.AbstractInput;
import com.gavilanvillar.engine.Input;

import java.util.ArrayList;
import java.util.List;

public class AInput extends AbstractInput {

    public AInput(View view){

        _touchEventList = new ArrayList<>();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TouchEvent event = new TouchEvent();
                event._x = (int)motionEvent.getX();
                event._y = (int)motionEvent.getY();

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        event._type = EventType.PULSADO;
                        break;
                    case MotionEvent.ACTION_UP:
                        event._type = EventType.LIBERADO;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        event._type = EventType.DESPLAZADO;
                        break;
                    default:
                        break;
                }
                synchronized (this) {
                    addEvent(event);
                }

                return  true;
            }


        });
    }
}
