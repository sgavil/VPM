package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sprite;

import java.util.List;

/**
 * Estado del juego donde se encuentran las instrucciones y los controles para jugar
 */
public class Tutorial extends GenericGameState {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos constantes (de Tutorial)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    //Posiciones de los Sprites
    private final int TAP_TO_PLAY_POS_Y = 1464;
    private final int HOW_TO_PLAY_POS_Y = 290;
    private final int INSTRUCTIONS_POS_Y = 768;



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de inicialización (de Tutorial)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Tutorial(Game game) {

        super(game);

    }

    /**
     * Se llama al init de GenericGameState y se obtienen los sprites específicos de este estado
     *
     * @param resourceManager Gestor de recursos para poder obtener los sprites
     */
    public void init(ResourceManager resourceManager) {

        super.init(resourceManager);

        _howToPlay = _resourceManager.getHowToPlay();
        _instructions = _resourceManager.getInstructions();

        _game.getAudio().muteAll();
    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de Tutorial)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Realiza la renderizacion de los Sprites del GameState
     */
    @Override
    public void render() {

        _game.getGraphics().clear(0xFF000000);

        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _howToPlay.drawCentered(_game.getGraphics(), HOW_TO_PLAY_POS_Y, 0, 1);
        _instructions.drawCentered(_game.getGraphics(), INSTRUCTIONS_POS_Y, 0, 1);

        _tapToPlay.drawCentered(_game.getGraphics(), TAP_TO_PLAY_POS_Y, 0, fadeInOutAlpha);
    }

    /**
     * Gestiona la pulsacion en la ventana
     */
    @Override
    public void handleEvent() {
        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e : ev) {
            if (e._type == EventType.PULSADO)
                System.out.print("PULSADOOOOOOOOOOOOOOOOOO \n");
            else if (e._type == EventType.LIBERADO) {
                _changeStateSound.play();

                SwitchDash s = new SwitchDash(_game);
                s.init(_resourceManager);
                _game.getGameStateManager().setState(s);
            } else if (e._type == EventType.DESPLAZADO)
                System.out.print("DESPLAZADOOOOOOOOOOOOOO \n");
        }
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de Tutorial)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Sprite _howToPlay = null;
    private Sprite _instructions = null;
}