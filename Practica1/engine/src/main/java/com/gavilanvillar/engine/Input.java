package com.gavilanvillar.engine;

import java.util.ArrayList;
import java.util.List;

public interface Input {
    class TouchEvent{
        public TouchEvent(int x,int y){
            _x = x;
            _y = y;
        }
        private int _x;
        private int _y;

        public int getX() {
            return _x;
        }
        public int getY(){
            return _y;
        }
    }
    List<TouchEvent> getTouchEvents();


}
