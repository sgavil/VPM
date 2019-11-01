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
                _touchEvents.add(new TouchEvent((int)motionEvent.getX(),(int)motionEvent.getY()));
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
