package com.gavilanvillar.desktop_engine;


import com.gavilanvillar.engine.AbstractInput;
import java.util.ArrayList;

/**
 * Clase que Hereda de AbstractInput y se encarga de transformar eventos de Raton en
 * TouchEvents y añadirlos a la lista de eventos
 */
public class PCInput extends AbstractInput {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos de inicialización (PCInput)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public PCInput(){

        _touchEventList = new ArrayList<>();

        _mouseController = new MouseController();

    }

    public void init(){
        _mouseController.init(this);
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //       Getters y Setters (de PCInput)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public MouseController getMouseController(){
        return _mouseController;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //       Atributos protegidos/privados (de PCInput)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   private MouseController _mouseController = null;


}
