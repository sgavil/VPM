package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Sound;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Ball.BALL_COLOR;
import com.gavilanvillar.game_logic.Player.PLAYER_COLOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gestiona el tratamiento de las bolas: movimiento,renderizado y comprobación de colisiones.
 * Aumenta la puntuación del jugador de la clase SwitchDash, además, controla el factor de incremento de la velocidad de las bolas y
 * de las flechas de fondo.
 * <p>
 * Este manager realiza Object Pooling a la hora de gestionar las bolas de cara a un mejor rendimiento
 */
public class BallsManager {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Atributos Constantes (de BallManager)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //Distancia de separación (en pixeles ) entre cada instanciacion de las bolas
    private final int BALL_SEPARATION = 395;

    //Velocidad vertical inicial con la que empiezan las bolas
    private final int INITIAL_BALL_VEL = 430;

    //Probabilidad de que se instancie una bola del mismo color que la anterior
    private final int SAME_COLOR_PERCENTAGE = 70;

    //Puntos relativos a la puntuacion necesarios para que aumente la velocidad de la bola
    private int SCORE_TO_LEVEL_UP = 10;

    //Incremento de la velocidad vertical de las bolas al conseguir SCORE_TO_LEVEL_UP puntos mas
    private int BALLS_SPEED_INCR = 90;


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Métodos de inicialización (de BallManager)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Inicializacion de BallsManager
     *
     * @param whiteSprite Sprite de la bola blanca
     * @param blackSprite Sprite de la bola negra
     * @param player      Referencia al jugador ( pala que recoge bolas )
     */
    public BallsManager(Sprite whiteSprite, Sprite blackSprite, Player player) {

        this._whiteSprite = whiteSprite;
        this._blackSprite = blackSprite;

        this._objs = new ArrayList<>();

        this._lasGeneratedBall = getObject();

        this._ballVel = INITIAL_BALL_VEL;

        this._particleSystem = new ParticleSystem(whiteSprite, blackSprite);

    }

    /**
     * Obtiene el color de la siguiente bola en función a la probabilidad SAME_COLOR_PERCENTAGE
     *
     * @return Color de la proxima bola a instanciar
     */
    private BALL_COLOR getNextColor() {

        BALL_COLOR bColor;

        if (!_objs.isEmpty()) {
            //True si se generará una bola del mismo color, false en caso contrario
            boolean preColorProbability = new Random().nextInt(100) < SAME_COLOR_PERCENTAGE;

            BALL_COLOR differentColor = (_lasGeneratedBall.getBallColor() == BALL_COLOR.BLACK)
                    ? BALL_COLOR.WHITE : BALL_COLOR.BLACK;

            bColor = (preColorProbability) ?
                    _lasGeneratedBall.getBallColor() : differentColor;
        } else
        //Si la lista esta vacia genera una bola de un color aleatorio ( esto quiere decir que el juego no ha comenzado )
        {
            int randomColor = (int) Math.floor(Math.random() * 2);
            bColor = (randomColor == 0) ? BALL_COLOR.BLACK : BALL_COLOR.WHITE;
        }

        return bColor;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Getter y Setters  (de BallManager)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Setter de la pala del jugador
     *
     * @param p Referencia al jugador ( la pala )
     */
    public void setPlayer(Player p) {

        this._player = p;

    }

    /**
     * Setter de la clase SwitchDash
     *
     * @param s Referencia al objecto SwitchDash
     */
    public void setSwitchDash(SwitchDash s) {
        _switchDash = s;
    }

    /**
     * Metodo encargado del Object Pooling, si la lista _objs esta vacia o no hay ningun elemento desactivado crea una bola nueva.
     * En caso contrario, activa el elemento encontrado y cambia su color si es necesario.
     *
     * @return Devuelve una bola de la pool de bolas con su color correspondiente.
     */
    public Ball getObject() {

        int i = 0;

        //Si no encuentra un objecto inactivo pasa al siguiente
        while (i < _objs.size() && _objs.get(i).isActive()) {
            i++;
        }

        BALL_COLOR bColor = getNextColor();
        Ball b;

        //Se ha recorrido toda la lista sin encontrar ninguno inactivo, se pasa a crear uno
        if (i >= _objs.size()) {

            b = (bColor == BALL_COLOR.BLACK) ?
                    new Ball(_blackSprite, bColor) : new Ball(_whiteSprite, bColor);

            b.setActive(true);
            _objs.add(b);

            //Se ha encontrado un elemento inactivo, se activa y se cambia su color
        } else {
            b = _objs.get(i);

            b.setColor(bColor);

            b.setSprite((bColor == BALL_COLOR.BLACK) ? _blackSprite : _whiteSprite);
            b.setActive(true);
        }

        return b;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Métodos privados/públicos (de BallManager)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Se encarga de generar una bola si la ultima bola generada esta a una distancia de BALL_SEPARATION pixeles
     */
    private void generateBall() {
        if (_lasGeneratedBall.getPosY() >= BALL_SEPARATION) {
            _lasGeneratedBall = getObject();
            _lasGeneratedBall.setPosY(0);
        }
    }

    /**
     * Instancia las bolas, aumenta la velocidad de estas, incrementa la puntuacion cuando la bola colisiona con la pala y gestiona
     * el fin del juego.
     *
     * @param deltaTime Tiempo transcurrido desde el frame anterior en milisegundos
     */
    public void update(double deltaTime) {

        generateBall();
        levelUpBalls();

        //Por cada bola activa se aumenta su posicion en Y, se comprueba si ha colisionado con la pala, se incrementa la puntuacion
        // y en caso de haber sobrepasado la pala se llama al gameOver
        for (int i = 0; i < _objs.size(); i++) {
            //System.out.println("La bola [" + i + "] esta " + _objs.get(i).isActive() );
            Ball b = _objs.get(i);
            if (b.isActive()) {
                //En el checkcollisionWith se comprueba si son del mismo color
                if (b.checkCollisionWith(_player)) {
                    // Coinciden el color de la bola y el player
                    if ((_player.getColor() == PLAYER_COLOR.BLACK && b.getBallColor() == BALL_COLOR.BLACK) ||
                            (_player.getColor() == PLAYER_COLOR.WHITE && b.getBallColor() == BALL_COLOR.WHITE)) {
                        b.setActive(false);
                        _ballLevelUpScore++;
                        _switchDash.addScore();
                        _takeBall.play();

                        _particleSystem.createParticles(b);
                    }
                    // No coinciden
                    else{
                        // Fin del juego
                        _switchDash.gameOver();
                    }
                }

                b.setPosY((float)(b.getPosY() + _ballVel * deltaTime));

            }

        }

        _particleSystem.update(deltaTime);

    }

    /**
     * Renderiza cada bola activa centrada en su correspondiente posicion Y
     *
     * @param g Graphics del engine para poder pintar los sprites de las bolas
     */
    public void render(Graphics g) {
        for (Ball b : _objs) {
            if (b.isActive()) {
                b.getBallSprite().drawCentered(g, b.getPosY(), 0, b.getAlpha());
            }
        }

        _particleSystem.render(g);
    }

    /**
     * Si se han conseguido SCORE_TO_LEVEL_UP puntos más se incrementa la velocidad de la bola y del fondo
     */
    private void levelUpBalls() {
        if (_ballLevelUpScore == SCORE_TO_LEVEL_UP) {
            _ballVel += BALLS_SPEED_INCR;
            _ballLevelUpScore = 0;
            _switchDash.speedUpArrows();
        }
    }

    /**
     * Metodo para asignar un sonido a la colision de la bola con la pala
     */
    public void setCollSound(Sound s) {
        _takeBall = s;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Atributos privados  (de BallManager)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    //Lista de objetos para hacer el pooling
    private List<Ball> _objs;

    //Sprites de las bolas
    private Sprite _whiteSprite;
    private Sprite _blackSprite;

    //Velocidad actual de todas las bolas
    private int _ballVel;

    //Referencia a la ultima bola generada
    private Ball _lasGeneratedBall;

    //Contador relativo de la puntuacion necesaria para aumentar las velocidades
    private int _ballLevelUpScore = 0;

    private SwitchDash _switchDash;
    private Player _player;
    private ParticleSystem _particleSystem = null;

    //Ball Collision sound
    private Sound _takeBall;


}
