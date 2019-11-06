package com.gavilanvillar.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInput implements Input {

    @Override
    //    Con "synchronized" le estamos diciendo a la máquina virtual que antes de que una hebra entre, bloquea el semáforo
    //    del objeto y cuando salga (salgas como salgas) libera
    synchronized public List<TouchEvent> getTouchEvents(){
        // Crea una copia de _touchEventList
        List<TouchEvent> newList = new ArrayList<>(_touchEventList);

        // Vacia _touchEventList
        _touchEventList.clear();

        // Devuelve la copia
        return newList;
    }

     public void addEvent(TouchEvent e){
       synchronized(this){
           _touchEventList.add(e);
       }
    }


    protected List<TouchEvent> _touchEventList;
}
