package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.GameState;
import com.gavilanvillar.engine.ResourceManager;
import com.gavilanvillar.engine.Sound;
import com.gavilanvillar.engine.Sprite;

/**
 * Clase abstracta que sirve como estado generico del juego ya que comparte parte de la funcionalidad
 * entre estados.
 */
public abstract class GenericGameState implements GameState {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos constantes (de GenericGameState)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //Tamaño logico del juego
    protected final int WIDTH_RES = 1080;
    protected final int HEIGHT_RES = 1920;

    //Velocidad a la que incrementan las flechas al subir de nivel
    private final int ARROWS_VEL_INCR = 90;


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //  Métodos de inicialización (de GenericGameState)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public GenericGameState(Game game) {
        this._game = game;
    }

    /**
     * Metodo que inicializa el estado, generando un fondo aleatorio y obtiene los Sprites necesarios
     *
     * @param resourceManager
     */
    public void init(ResourceManager resourceManager) {
        this._resourceManager = resourceManager;

        this._game.getGraphics().setLogicResolution(WIDTH_RES, HEIGHT_RES);

        _changeStateSound = resourceManager.getGota();


        int randomBackground = (int) Math.floor(Math.random() * _resourceManager.getBackgrounds().length);
        this._actualBackground = _resourceManager.getBackgrounds()[randomBackground];

        this._arrowsBackground = _resourceManager.getArrowsBackground();
        this._arrowsPosY_0 = 0 - _arrowsBackground.getImage().getHeight();
        this._arrowsPosY_1 = 0;

        this._tapToPlay = _resourceManager.getTapToPlay();
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos públicos (de GenericGameState)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Actualiza la posicion de las flechas y realiza una transicion de alpha para la imagen de playAgain
     *
     * @param deltaTime Tiempo transcurrido desde el ultimo frame en milisegundos
     */
    @Override
    public void update(double deltaTime) {

        fadeInFadeOutAlpha(deltaTime);
        arrowsMovement(deltaTime);
    }


    public void speedUpArrows() {
        _arrowsVel += ARROWS_VEL_INCR;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Métodos protegidos (de GenericGameState)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Realiza la animación del fondo de flechas
     *
     * @param deltaTime Tiempo transcurrido desde el ultmo frame en milisegundos
     */
    protected void arrowsMovement(double deltaTime) {
        int newArrowsPos = 0;
        newArrowsPos += (_arrowsVel * deltaTime);
        _arrowsPosY_0 += newArrowsPos;
        _arrowsPosY_1 += newArrowsPos;

        if (_arrowsPosY_0 >= HEIGHT_RES && _arrowsPosY_1 >= 0)
            _arrowsPosY_0 = _arrowsPosY_1 - _arrowsBackground.getImage().getHeight();
        if (_arrowsPosY_1 >= HEIGHT_RES && _arrowsPosY_0 >= 0)
            _arrowsPosY_1 = _arrowsPosY_0 - _arrowsBackground.getImage().getHeight();
    }

    /**
     * Realiza el efecto de parpadeo pasando un valor alpha de 0.0 a 1.0 y de 1.0 a 0.0
     *
     * @param deltaTime Tiempo entre frames en milisegundos
     */
    protected void fadeInFadeOutAlpha(double deltaTime) {
        double deltaAlphaTime = deltaAlpha * deltaTime;

        if (fadeOut)
            fadeInOutAlpha -= (deltaAlphaTime);
        else if (fadeIn)
            fadeInOutAlpha += (deltaAlphaTime);

        //Al redimensionar arrastrando lentamente el valor del deltatime aumenta haciendo que los valores se descontrolen fuera del rango permitido del alpha
        // para evitar esto hacemos un clamp entre el rango minimo y maximo que permite el AlphaComposite

        if (fadeInOutAlpha + (deltaAlphaTime) >= 1.0f) {
            fadeOut = true;
            fadeIn = false;
            fadeInOutAlpha = 1.0f;

        } else if (fadeInOutAlpha - (deltaAlphaTime) <= 0.0f) {
            fadeOut = false;
            fadeIn = true;
            fadeInOutAlpha = 0.0f;
        }

    }

    /**
     * Aumenta la velocidad de las flechas cuando se aumenta de nivel
     */


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //   Atributos privados/protegidos  (de GenericGameState)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    protected Game _game;
    protected ResourceManager _resourceManager;

    protected int _arrowsVel = 384;

    //Posicion del fondo de flechas
    protected int _arrowsPosY_0 = 0;
    protected int _arrowsPosY_1 = 0;

    private float deltaAlpha = 1.2f;

    //Valor que se utiliza como alfa si se quiere hacer un efecto de parpadeo
    protected float fadeInOutAlpha = 1.0f;

    private boolean fadeIn = false;
    private boolean fadeOut = true;

    protected Sprite _actualBackground;
    protected Sprite _arrowsBackground;

    protected Sprite _tapToPlay;

    protected Sound _changeStateSound;



}
