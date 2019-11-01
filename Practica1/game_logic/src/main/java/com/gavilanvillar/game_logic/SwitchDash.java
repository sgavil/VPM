package com.gavilanvillar.game_logic;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Game;
import com.gavilanvillar.engine.GameState;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.Sprite;

public class SwitchDash implements GameState {
    private final int WIDTH_RES = 1080;
    private final int HEIGHT_RES = 1920;

    private final int BACKGROUNDS_HORIZONTAL = 9;

    private final int BALLS_HORIZONTAL = 10;
    private final int BALLS_VERTICAL = 2;

    private final int BUTTONS_HORIZONTAL = 10;

    private final int PLAYERS_VERTICAL = 2;

    public SwitchDash(Game game) {
        this._game = game;
    }

    public void init() {
        ((AbstractGraphics) _game.getGraphics()).setLogicResolution(WIDTH_RES, HEIGHT_RES);
        loadResources();
    }

    /**
     * Método que carga todos los recursos gráficos que encuentra en la carpeta "assets/sprites/"
     */
    private void loadResources() {

        loadBGs();

        loadPlayers();

        loadBalls();

        loadMenuSprites();

        /*image = _game.getGraphics().newImage("sprites/buttons.png");
        //_buttons = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));*/



        /*image = _game.getGraphics().newImage("sprites/scoreFont.png");
        _scoreFont = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));*/



        /*image = _game.getGraphics().newImage("sprites/white.png");
        _white = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));*/

    }

    private void loadBGs() {

        Image image = _game.getGraphics().newImage("sprites/arrowsBackground.png");
        _arrowsBackground = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight()));

        image = _game.getGraphics().newImage("sprites/backgrounds.png");

        _greenBackground = new Sprite(image, new Rect(0, (image.getWidth() / BACKGROUNDS_HORIZONTAL), 0, image.getHeight()));
        _emeraldBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL), (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 2, 0, image.getHeight()));
        _cyanBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 2, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 3, 0, image.getHeight()));
        _blueBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 3, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 4, 0, image.getHeight()));
        _purpleBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 4, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 5, 0, image.getHeight()));
        _hardBlueBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 5, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 6, 0, image.getHeight()));
        _yellowBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 6, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 7, 0, image.getHeight()));
        _redBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 7, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 8, 0, image.getHeight()));
        _greyBackground = new Sprite(image, new Rect((image.getWidth() / BACKGROUNDS_HORIZONTAL) * 8, (image.getWidth() / BACKGROUNDS_HORIZONTAL) * 9, 0, image.getHeight()));
    }

    private void loadPlayers() {

        Image image = _game.getGraphics().newImage("sprites/players.png");

        _whitePlayer = new Sprite(image, new Rect(0, image.getWidth(), 0, image.getHeight() / PLAYERS_VERTICAL));
        _blackPlayer = new Sprite(image, new Rect(0, image.getWidth(), image.getHeight() / PLAYERS_VERTICAL, image.getHeight()));

    }

    private void loadBalls() {
        Image image = _game.getGraphics().newImage("sprites/balls.png");

        _whiteBall = new Sprite(image, new Rect(0, image.getWidth() / BALLS_HORIZONTAL, 0, image.getHeight() / BALLS_VERTICAL));
        _blackBall = new Sprite(image, new Rect(0, image.getWidth() / BALLS_HORIZONTAL, image.getHeight() / BALLS_VERTICAL, image.getHeight() / BALLS_VERTICAL));
    }

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

    public void orientationChanged(boolean portrait) {
        ((AbstractGraphics) _game.getGraphics()).swapPhysicResolution();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(double deltaTime) {
        _greenBackground.draw(_game.getGraphics(), new Rect(0, WIDTH_RES, 0, HEIGHT_RES));
        _whiteBall.draw(_game.getGraphics(), new Rect(0, 32, 0, 32));
        _whitePlayer.drawCentered(_game.getGraphics(), _whitePlayer.getRect(), 1700, 0);
    }

    private Game _game;

    //Backgrounds
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

    //Balls
    private Sprite _whiteBall = null;
    private Sprite _blackBall = null;

    //Buttons TODO:Seleccionar los que hacen falta ( no son todos )

    //Menus
    private Sprite _gameOver = null;
    private Sprite _howToPlay = null;
    private Sprite _instructions = null;
    private Sprite _playAgain = null;
    private Sprite _switchDashLogo = null;
    private Sprite _tapToPlay = null;

    //Players
    private Sprite _whitePlayer = null;
    private Sprite _blackPlayer = null;


}
