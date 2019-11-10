package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Button;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sound;
import com.gavilanvillar.engine.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Estado inicial de la aplicación donde se muestra el logo y se permite empezar a jugar,
 * quitar el sonido o ir a la pantalla de controles
 */
public class Menu extends GenericGameState {


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de inicialización (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Menu(Game game) {
        super(game);

    }

    /**
     * Se llama al init de GenericGameState y se obtienen los sprites específicos de este estado
     *
     * @param resourceManager Gestor de recursos para poder obtener los sprites
     */
    public void init(ResourceManager resourceManager) {

        super.init(resourceManager);

        _switchDashLogo = _resourceManager.getSwitchDashLogo();

         _soundMuted = resourceManager.getMutedIcon();
        _soundUnMuted = resourceManager.getNotMutedIcon();

        _soundButton = new Button(_soundUnMuted);



    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de públicos (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Realiza la renderizacion de los Sprites del GameState
     **/
    @Override
    public void render() {
        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _switchDashLogo.drawCentered(_game.getGraphics(), 356, 0, 1.0f);
        _tapToPlay.drawCentered(_game.getGraphics(), 950, 0, fadeInOutAlpha);

        _soundButton.getSprite().draw(_game.getGraphics(),0,700,1.0f);
    }

    /**
     * Gestiona la pulsacion en la ventana para empezar a jugar,quitar el volumen o ir a las instrucciones
     */
    @Override
    public void handleEvent() {

        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e : ev) {
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO)
            {
                if(_soundButton.isClicked(e._x,e._y))
                {
                    if(_isSoundMuted) _soundButton.changeSprite(_soundUnMuted);
                    else _soundButton.changeSprite(_soundMuted);

                    _isSoundMuted = !_isSoundMuted;
                }
                //Se ha hecho clic fuera de los botones
                else {
                    _changeStateSound.play();
                    Tutorial s = new Tutorial(_game);
                    s.init(_resourceManager);
                    _game.getGameStateManager().setState(s);
                }


            } else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Sprite _switchDashLogo = null;

    private Button _soundButton;

    private Sprite _soundMuted;
    private Sprite _soundUnMuted;

    private boolean _isSoundMuted = false;
}
