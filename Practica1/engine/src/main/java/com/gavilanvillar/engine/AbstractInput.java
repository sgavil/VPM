package com.gavilanvillar.engine;

import java.util.List;

public abstract class AbstractInput implements Input {

    @Override
    synchronized public List<TouchEvent> getTouchEvents(){
        /*
        * Crea una lista nueva con el contenido de _list.
        * Vaciar _list
        * Devolver copia
        * */

        return _list;
    }

    /*
    Con "synchronized" le estamos diciendo a la máquina virtual que antes de que una hebra entre, bloquea el semáforo
    del objeto y cuando salga (salgas como salgas) libera
    * protected void addEvent(TouchEvent e){
    *   synchronized(this){
    *       _list.add(e);
    *   }
    * }
    * */

    protected List<TouchEvent> _list;
}
