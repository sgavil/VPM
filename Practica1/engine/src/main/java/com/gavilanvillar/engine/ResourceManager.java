package com.gavilanvillar.engine;

/**
 * Clase ResourceManager
 *
 * Carga los recursos que se encuentran en la carpeta "assets"
 *
 * Si se quiere cargar un recurso nuevo se debe hacer su inicializacion y
 * un getter.
 */
public class ResourceManager {


    public ResourceManager(Game game) {
        this._game = game;
    }

    public void init() {
        loadResources();
    }

    /**
     * Método que carga todos los recursos gráficos
     */
    private void loadResources() {

        loadBGs();

        loadPlayers();

        loadBalls();

        loadMenuSprites();

        loadSounds();

        loadMusic();

        loadButtons();

        loadFont();


        //TODO: cargar los botones ya la score

    }

    private void loadButtons(){
        Image image = _game.getGraphics().newImage("sprites/buttons.png");

        _soundNotMuted = new Sprite(image, new Rect((image.getWidth() / BUTTONS_HORIZONTAL) * 2,
                (image.getWidth() / BUTTONS_HORIZONTAL) * 3, 0, image.getHeight()));
       _soundMuted = new Sprite(image, new Rect((image.getWidth() / BUTTONS_HORIZONTAL) * 3,
               (image.getWidth() / BUTTONS_HORIZONTAL) * 4, 0, image.getHeight()));
    }
    /**
     * Carga los sonidos de la carpeta sounds
     */
    private void loadSounds() {
        _disparoSound = _game.getAudio().newSound("sounds/disparo.wav");
        _gotaSound = _game.getAudio().newSound("sounds/gota.wav");

    }

    /**
     * Carga la musica de la carpeta music
     */
    private void loadMusic(){
        _mainMusic = _game.getAudio().newSound("music/mainTheme.wav");
    }

    /**
     * Carga los fondos
     */
    private void loadBGs() {
        Image image = null;

        image = _game.getGraphics().newImage("sprites/arrowsBackground.png");
        _arrowsBackground = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/white.png");
        _white = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/backgrounds.png");

        _greenBackground = new Sprite(image, new Rect(0, (image.getWidth() / BACKGROUNDS_HORIZONTAL),
                0, image.getHeight()));
        _emeraldBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL),
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 2, 0, image.getHeight()));
        _cyanBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 2,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 3, 0, image.getHeight()));
        _blueBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 3,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 4, 0, image.getHeight()));
        _purpleBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 4,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 5, 0, image.getHeight()));
        _hardBlueBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 5,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 6, 0, image.getHeight()));
        _yellowBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 6,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 7, 0, image.getHeight()));
        _redBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 7,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 8, 0, image.getHeight()));
        _greyBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 8,
                (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 9, 0, image.getHeight()));

        _backgrounds = new Sprite[]{_greenBackground, _emeraldBackground, _cyanBackground,
                _blueBackground, _purpleBackground, _hardBlueBackground, _yellowBackground,
                _redBackground, _greyBackground};
    }

    /**
     * Carga los players
     */
    private void loadPlayers() {

        Image image = _game.getGraphics().newImage("sprites/players.png");

        _whitePlayer = new Sprite(image, new Rect(0, image.getWidth(),
                0, image.getHeight() / PLAYERS_VERTICAL));
        _blackPlayer = new Sprite(image, new Rect(0, image.getWidth(),
                image.getHeight() / PLAYERS_VERTICAL, image.getHeight()));

    }

    /**
     * Carga las imagenes de las pelotas
     */
    private void loadBalls() {
        Image image = _game.getGraphics().newImage("sprites/balls.png");

        _whiteBall = new Sprite(image, new Rect(0, image.getWidth() / BALLS_HORIZONTAL,
                BALL_SIZE - BALL_REAL_SIZE, image.getHeight() / BALLS_VERTICAL));
        _blackBall = new Sprite(image, new Rect(0, image.getWidth() / BALLS_HORIZONTAL,
                (image.getHeight() / BALLS_VERTICAL) + BALL_SIZE - BALL_REAL_SIZE, image.getHeight()));
    }

    /**
     * Carga las imagenes relacionadas con los menus como el logo, imagen de instrucciones...
     */
    private void loadMenuSprites() {
        Image image;

        image = _game.getGraphics().newImage("sprites/gameOver.png");
        _gameOver = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/howToPlay.png");
        _howToPlay = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/instructions.png");
        _instructions = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/playAgain.png");
        _playAgain = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/switchDashLogo.png");
        _switchDashLogo = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/tapToPlay.png");
        _tapToPlay = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));
    }


    private void loadFont(){

        Image image;

        image = _game.getGraphics().newImage("sprites/scoreFont.png");
        _scoreFont = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        _letters = new Sprite[26];
        _numbers = new Sprite[10];
        _punctuationSign = new Sprite[30];

        int numX = 15;
        int numY = 7;

        int diffX = (GLIFO_WIDTH - GLIFO_REAL_WIDTH) / 2;
        int diffY = (GLIFO_HEIGHT - GLIFO_REAL_HEIGHT) / 2;

        int m = 0;
        int letter = 0;
        int number = 0;
        int punct = 0;

        for(int y = 0; y < numY && m < 92; y++){
            for(int x = 0; x < numX && m < 92; x ++){
                if (m < 26){
                    _letters[letter] = new Sprite(image, new Rect((x * GLIFO_WIDTH) + diffX,
                            (x * GLIFO_WIDTH) + GLIFO_WIDTH - diffX,
                            (y * GLIFO_HEIGHT) + diffY,
                            (y * GLIFO_HEIGHT) + GLIFO_HEIGHT));
                    letter++;
                }
                else if (m >= 26 * 2 && m < (26 * 2) + 10){
                    _numbers[number] = new Sprite(image, new Rect((x * GLIFO_WIDTH) + diffX,
                            (x * GLIFO_WIDTH) + GLIFO_WIDTH - diffX,
                            (y * GLIFO_HEIGHT) + diffY,
                            (y * GLIFO_HEIGHT) + GLIFO_HEIGHT));
                    number++;
                }
                else if (m >= (26 * 2) + 10){
                    _punctuationSign[punct] = new Sprite(image, new Rect((x * GLIFO_WIDTH) + diffX,
                            (x * GLIFO_WIDTH) + GLIFO_WIDTH - diffX,
                            (y * GLIFO_HEIGHT) + diffY,
                            (y * GLIFO_HEIGHT) + GLIFO_HEIGHT));
                    punct++;
                }
                m++;
            }
        }

    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                          Getters
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public Sprite[] getBackgrounds() {
        return _backgrounds;
    }

    public Sprite getArrowsBackground() {
        return _arrowsBackground;
    }

    public Sprite getWhite() {
        return _white;
    }

    public Sprite getWhitePlayer() {
        return _whitePlayer;
    }
    public Sprite getBlackPlayer() {
        return _blackPlayer;
    }

    public Sprite getSwitchDashLogo() {
        return _switchDashLogo;
    }

    public Sprite getTapToPlay() {
        return _tapToPlay;
    }

    public Sprite getHowToPlay() {
        return _howToPlay;
    }

    public Sprite getInstructions() {
        return _instructions;
    }

    public Sprite getWhiteBall() {
        return _whiteBall;
    }
    public Sprite getBlackBall() {
        return _blackBall;
    }

    public Sprite getGameOver() {
        return _gameOver;
    }

    public Sprite getPlayAgain() {
        return _playAgain;
    }

    public Sprite getMutedIcon(){return _soundMuted;}
    public Sprite getNotMutedIcon() {return  _soundNotMuted;}

    public Sprite[] getNumbers() { return _numbers; }
    public Sprite[] getLetters() { return _letters; }
    public Sprite[] getPunctuationSign() { return _punctuationSign; }

    //Getters Sonidos
    public Sound getDisparo() {return _disparoSound;}
    public Sound getGota() {return _gotaSound;}

    //Getters musica
    public Sound getTecnhoLoop() {
        return _mainMusic;
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //              Atributos privados
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Game _game;

    //Numero de celdas que tiene cada imagen
    private final int BACKGROUNDS_HORIZONTAL = 9;

    private final int BALLS_HORIZONTAL = 10;
    private final int BALLS_VERTICAL = 2;
    private final int BALL_SIZE = 128;
    private final int BALL_REAL_SIZE = 128;

    private final int BUTTONS_HORIZONTAL = 10;

    private final int PLAYERS_VERTICAL = 2;

    private final int LETTERS = 26;
    private final int NUMBERS = 10;
    private final int PUNCTUATIONS_SIGN = 30;
    private final int GLIFO_WIDTH = 125;
    private final int GLIFO_HEIGHT = 160;
    private final int GLIFO_REAL_WIDTH = 93;
    private final int GLIFO_REAL_HEIGHT = 112;

    //Fondos
    private Sprite[] _backgrounds = null;
    private Sprite _greenBackground;
    private Sprite _emeraldBackground = null;
    private Sprite _cyanBackground = null;
    private Sprite _blueBackground = null;
    private Sprite _purpleBackground = null;
    private Sprite _hardBlueBackground = null;
    private Sprite _yellowBackground = null;
    private Sprite _redBackground = null;
    private Sprite _greyBackground = null;

    private Sprite _arrowsBackground = null;
    private Sprite _white = null;

    //Bolas
    private Sprite _whiteBall = null;
    private Sprite _blackBall = null;

    //Buttons TODO:Seleccionar los que hacen falta ( no son todos )

    private Sprite _soundMuted;
    private Sprite _soundNotMuted;

    //Menus
    private Sprite _gameOver = null;
    private Sprite _howToPlay = null;
    private Sprite _instructions = null;
    private Sprite _playAgain = null;
    private Sprite _switchDashLogo = null;
    private Sprite _tapToPlay = null;

    //Font
    private Sprite _scoreFont = null;
    private Sprite[] _numbers = null;
    private Sprite[] _letters = null;
    private Sprite[] _punctuationSign = null;

    //Players
    private Sprite _whitePlayer = null;
    private Sprite _blackPlayer = null;


    //Sonidos
    private Sound _disparoSound = null;
    private Sound _gotaSound = null;

    //Musica
    private Sound _mainMusic;


}