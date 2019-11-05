package com.gavilanvillar.android_engine;

import android.view.MotionEvent;
import android.view.View;

import com.gavilanvillar.engine.Input;

import java.util.ArrayList;
import java.util.List;

public class AInput implements Input {

    public AInput(View view){

        _touchEvents = new ArrayList<>();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TouchEvent event = new TouchEvent();
                event._x = (int)motionEvent.getX();
                event._y = (int)motionEvent.getY();
                _touchEvents.add(event);

                return  true;
            }


        });
    }
    @Override
    public List<TouchEvent> getTouchEvents() {
        return _touchEvents;
    }
    public void clearEvents(){_touchEvents.clear();}
    private List<TouchEvent> _touchEvents;
}
