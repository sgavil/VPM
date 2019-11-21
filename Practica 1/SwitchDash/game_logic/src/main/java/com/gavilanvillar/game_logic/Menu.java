package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Button;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.Input.EventType;
import com.gavilanvillar.engine.Input.TouchEvent;
import com.gavilanvillar.engine.Music;
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
    //   Atributos constantes (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private final int SOUND_ICON_POS_X = 30;
    private final int QUESTION_ICON_POS_X = 910;
    private final int ICON_POS_Y = 30;

    private final int FULLSCREEN_KEYCODE = 70; // f


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

        _questionIcon = resourceManager.getQuestionIcon();
        _instructionsButton = new Button(_questionIcon);

        _menuTheme = resourceManager.getMenuTheme();

        _menuTheme.setLoop(true);
        _menuTheme.play();

        changeMutedIcon();


    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos de públicos (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public void render() {

        _game.getGraphics().clear(0xFF000000);

        _actualBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES,
                0, HEIGHT_RES), 1.0f);

        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_0, 0, 0.3f);
        _arrowsBackground.drawCentered(_game.getGraphics(), _arrowsPosY_1, 0, 0.3f);

        _switchDashLogo.drawCentered(_game.getGraphics(), 356, 0, 1.0f);
        _tapToPlay.drawCentered(_game.getGraphics(), 950, 0, fadeInOutAlpha);

        _soundButton.getSprite().draw(_game.getGraphics(), SOUND_ICON_POS_X, ICON_POS_Y, 1.0f);
        _instructionsButton.getSprite().draw(_game.getGraphics(), QUESTION_ICON_POS_X, ICON_POS_Y, 1.0f);

    }



    @Override
    public void handleEvent() {

        List<TouchEvent> ev = _game.getInput().getTouchEvents();

        for (TouchEvent e : ev) {
            if (e._type == EventType.LIBERADO) {
                // Se ha hecho click en el botón de sonido
                if (_soundButton.isClicked(e._x, e._y)) {

                    _game.getAudio().setSoundState(!_game.getAudio().isSoundMuted());
                    changeMutedIcon();
                }
                // Se ha hecho click en el botón de instrucciones
                else if (_instructionsButton.isClicked(e._x, e._y)) {

                    _changeStateSound.play();
                    _menuTheme.stop();
                    Tutorial s = new Tutorial(_game);
                    s.init(_resourceManager);
                    _game.getGameStateManager().setState(s);

                }
                // Se ha pulsado la tecla "F" en teclado para cambiar entre pantalla completa
                // y modo ventana
                else if (e._id == FULLSCREEN_KEYCODE) {
                    _fullscreen = !_fullscreen;
                    _game.setFullscreen(_fullscreen);
                }
                // Se ha hecho click en la pantalla
                else {
                    _changeStateSound.play();
                    _menuTheme.stop();
                    SwitchDash s = new SwitchDash(_game);
                    s.init(_resourceManager);
                    _game.getGameStateManager().setState(s);
                }
            }
        }

    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados (de Menu)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Sprite _switchDashLogo = null;

    // Botones
    private Button _instructionsButton = null;


    private Sprite _questionIcon = null;

    private boolean _fullscreen = false;

    private Music _menuTheme;

}
