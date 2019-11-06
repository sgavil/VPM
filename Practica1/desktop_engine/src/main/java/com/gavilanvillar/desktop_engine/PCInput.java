package com.gavilanvillar.desktop_engine;


import com.gavilanvillar.engine.AbstractInput;
import com.gavilanvillar.engine.Input;

import java.util.ArrayList;
import java.util.List;

public class PCInput extends AbstractInput {

    public PCInput(){

        _touchEventList = new ArrayList<>();

        _mouseController = new MouseController();

    }

    public void init(){
        _mouseController.init(this);
    }

    public MouseController getMouseController(){
        return _mouseController;
    }

    MouseController _mouseController = null;

    //MouseListener
    //MouseMotionListener
}
