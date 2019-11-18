package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.Sprite;
import com.gavilanvillar.game_logic.Ball.BALL_COLOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleSystem {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //              Atributos Constantes (de ParticleSystem)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    final int NUM_PARTICLES_TO_CREATE = 7; // Numero de bolas que se crean cada vez

    // Tamaño maximo y minimo que pueden adquirir las bolas
    final int MAX_SIZE = 150;
    final int MIN_SIZE = 40;
    final int DECREMENT_SIZE = 2; // Disminucion del tamaño con el tiempo

    final float INITIAL_ALPHA = 0.6f; // Alpha inicial
    final float DECREMENT_ALPHA = 0.6f; // Disminucion del alpha con el tiempo

    // Posicion inicial de la bola
    final int POS_X = 500;
    final int POS_Y = 1150;


    final int PARTICLES_GRAVITY = 500; // Velocidad de caida
    final int MAX_VEL_X = 300; // Velocidad en el eje X
    final int MAX_VEL_Y = -600; // Velocidad inicial, para que salga hacia arriba




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos de inicialización (ParticleSystem)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public ParticleSystem(Sprite white, Sprite black){

        this._particles = new ArrayList<>();
        this._whiteSprite = white;
        this._blackSprite = black;

    } // ParticleSystem




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos protegidos/privados (de ParticleSystem)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private void initilizeBall(Ball parentBall, Ball b){

        // Se activa la bola
        b.setActive(true);

        // Le pone un alpha inicial, 0.6f
        b.setAlpha(INITIAL_ALPHA);

        // Selecciona un tamaño random para la bola
        Random rand = new Random();
        int randomSize = rand.nextInt(MAX_SIZE - MIN_SIZE);
        randomSize += MIN_SIZE;
        b.setSize(randomSize);

        // Posiciona la bola en coordenadas del jeugo
        b.setPosX(POS_X);
        b.setPosY(POS_Y);

        // Da una velocidad a la bola
        int randomVelY = rand.nextInt(-MAX_VEL_Y);
        randomVelY = -randomVelY;
        int randomVelX = rand.nextInt(2 * MAX_VEL_X);
        randomVelX -= MAX_VEL_X;
        b.setVelX(randomVelX);
        b.setVelY(randomVelY);

    } // initilizeBall

    /**
     * Crea particulas
     * @param parentBall particula a partir de la cual se crean las otras
     */
    public void createParticles(Ball parentBall){

        // Lista que almacenará los index de los elementos inactivos de _particles
        List<Integer> ballsInactive = new ArrayList<>();

        // Analiza los elementos inactivos
        for(int i = 0; i < _particles.size(); i++){
            if(!_particles.get(i).isActive())
                ballsInactive.add(i);
        }

        // Activa los elementos inactivos
        for(int i = 0; i < ballsInactive.size(); i++) {
            Ball b = _particles.get(ballsInactive.get(i));

            b.setColor(parentBall.getBallColor());
            b.setSprite((parentBall.getBallColor() == BALL_COLOR.BLACK) ? _blackSprite : _whiteSprite);

            initilizeBall(parentBall, b);
        }

        // Crea las bolas que faltan hasta llegar al numero de bolas por cada colision
        for(int i = ballsInactive.size(); i < NUM_PARTICLES_TO_CREATE; i++){
            Ball b;
            if (parentBall.getBallColor() == BALL_COLOR.BLACK)
                b = new Ball(_blackSprite, parentBall.getBallColor());
            else
                b = new Ball(_whiteSprite, parentBall.getBallColor());

            initilizeBall(parentBall, b);

            _particles.add(b);
        }

    } // createParticles


    public void update(double deltaTime){

        for(Ball b: _particles){

            if (b.isActive()){

                // Actualiza el alpha y el tamaño de la bola
                b.setAlpha((float)(b.getAlpha() - (DECREMENT_ALPHA * deltaTime)));
                b.setSize((int)(b.getSize() - (DECREMENT_SIZE * deltaTime)));

                // Si es transparente o el tamaño es 0, se desactiva la bola
                if (b.getAlpha() <= 0.0f || b.getSize() <= 0)
                    b.setActive(false);
                else{

                    // Actualiza la posicion de la bola
                    b.setVelY((int)(b.getVelY() + (PARTICLES_GRAVITY * deltaTime)));
                    b.setPosY((int)(b.getPosY() + (b.getVelY() * deltaTime)));

                    b.setPosX((int)(b.getPosX() + (b.getVelX() * deltaTime)));

                }

            }

        }

    } // update

    public void render(Graphics g){

        for (Ball b : _particles) {

            if (b.isActive()) {

                // Pinta la bola
                b.getBallSprite().draw(g, new Rect(b.getPosX(),
                        b.getPosX() + b.getSize(),
                        b.getPosY(),
                        b.getPosY() + b.getSize()
                ), b.getAlpha());

            }

        }

    } // render




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //              Atributos privados  (ParticleSystem)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Sprite _whiteSprite;
    private Sprite _blackSprite;
    private List<Ball> _particles;

} // class ParticleSystem
