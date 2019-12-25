using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class BoardManager : MonoBehaviour
{
    // Publico
    public ResourceManager _resourceManager;
    public CanvasManager _canvasManager;

    public struct MatrixPos
    {
        public int _x;
        public int _y;

        public MatrixPos(int x, int y)
        {
            _x = x;
            _y = y;
        }
    }

    public GameObject _tileGameObject;
    public GameObject _hintGameObject;
    public GameObject _cursorGameObject;

    public int _tilesShowingWhenUserWantHint;

    // Privado
    private bool[,] _pressedTilesMatrix;
    private Tile[,] _tilesMatrix;
    private Hint[,] _hintVerticalArray;
    private Hint[,] _hintHorizontalArray;
    private Stack<MatrixPos> _pathStack;

    private int _boardHeight = 0;
    private int _boardWidth = 0;

    private int _tilesShowing;
    private int _hintCont = 0;

    public int ActualColor
    {
        get
        {
            return _actualColor;
        }
        set
        {
            _actualColor = value;
        }
    }


    // Privado
    private LevelData _levelData;
    private List<int> _levelAvailableSpace;
    private List<int> _levelSize;

    private MatrixPos _firsTile;
    private int _actualColor = 0;

    void Start()
    {

        _levelData = GameManager.Instance.GetLevel();
        _levelAvailableSpace = GameManager.Instance.GetAvailableSpace();
        _levelSize = GameManager.Instance.GetSize();

        _tilesShowing = _tilesShowingWhenUserWantHint;

        SetGridSize();

        InitializeMatrix();

        CreateGrid();
    }

    private void Update()
    {
        InputController();
        if (GameManager.Instance.IsScreenSizeChanged())
        {
            ScaleGridAndSetPosition();
            GameManager.Instance.SetScreenSizeIsChanged(false);
        }
    }

    /// <summary>
    /// Inicializa las matrices que almacenan los bool y los Tiles. Tambien inicializa la
    /// pila de Tiles.
    /// </summary>
    public void InitializeMatrix()
    {
        _pressedTilesMatrix = new bool[_boardHeight, _boardWidth];

        _tilesMatrix = new Tile[_boardHeight, _boardWidth];

        _hintHorizontalArray = new Hint[_boardHeight, _boardWidth - 1];
        _hintVerticalArray = new Hint[_boardHeight - 1, _boardWidth];

        _pathStack = new Stack<MatrixPos>();
    }

    /// <summary>
    /// Guarda el tamaño del tablero.
    /// </summary>
    /// <param name="gridSize">Tamaño del tablero</param>
    public void SetGridSize()
    {
        _boardWidth = _levelData._width;
        _boardHeight = _levelData._height;
    }

    /// <summary>
    /// Selecciona los colores por defecto y la piel del Tile y crea el tablero.
    /// </summary>
    /// <param name="categoryLevel">Categoria del nivel para crear</param>
    /// <param name="level">Nivel</param>
    void CreateGrid()
    {
        SetBoardColor();
        GenerateGrid();
    }

    /// <summary>
    /// Asigna el color por defecto y una piel aleatoria al GameObject Tile.
    /// </summary>
    void SetBoardColor()
    {
        _actualColor = UnityEngine.Random.Range(1, _resourceManager._blockScriptableObjects.Count);
        _cursorGameObject.GetComponent<SpriteRenderer>().sprite = _resourceManager._blockScriptableObjects[_actualColor].touch;

        Tile tile;
        if (_tileGameObject.TryGetComponent<Tile>(out tile))
        {
            tile._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].block;
            tile._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
        }

        Hint hint;
        if (_hintGameObject.TryGetComponent<Hint>(out hint))
        {
            hint._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].hint;
            hint._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].hint;
        }
    }

    /// <summary>
    /// Crea Tiles y los asigna a la matriz de Tiles, ademas modifica la matriz de bool
    /// en la que todos son false excepto el Tile pulsado al inicio y los Tiles que no existen.
    /// Por ultimo, modifica la posicion del tablero para centrarlo en el espacio.
    /// </summary>
    /// <param name="layout">Tablero</param>
    public void GenerateGrid()
    {
        GameObject tileParent = (GameObject)Instantiate(new GameObject(), transform);
        tileParent.name = "Tile Group";
        GameObject pathParent = (GameObject)Instantiate(new GameObject(), transform);
        pathParent.name = "Path Group";

        for (int y = 0; y < _boardHeight; y++)
        {
            for (int x = 0; x < _boardWidth; x++)
            {
                _pressedTilesMatrix[y, x] = true;
                if (_levelData._layout[y][x] != '0')
                {
                    GameObject tile = (GameObject)Instantiate(_tileGameObject, tileParent.transform);
                    float posX = x;
                    float posY = -y;

                    tile.transform.position = new Vector2(posX, posY);

                    if (_levelData._layout[y][x] == '1')
                    {
                        _pressedTilesMatrix[y, x] = false;
                    }
                    else if (_levelData._layout[y][x] == '2')
                    {
                        _firsTile = new MatrixPos(x, y);
                        _pathStack.Push(new MatrixPos(x, y));
                    }

                    _tilesMatrix[y, x] = tile.GetComponent<Tile>();
                    _tilesMatrix[y, x].SetPressed(_pressedTilesMatrix[y, x]);


                    if (x + 1 < _boardWidth && _levelData._layout[y][x + 1] != '0')
                    {
                        GameObject hint = (GameObject)Instantiate(_hintGameObject, pathParent.transform);
                        float hintPosX = posX + 0.5f;
                        hint.transform.position = new Vector3(hintPosX, posY);
                        hint.GetComponent<Hint>().SetClueActive(false);

                        _hintHorizontalArray[y, x] = hint.GetComponent<Hint>();

                        hint.SetActive(false);

                    }
                    if (y + 1 < _boardHeight && _levelData._layout[y + 1][x] != '0')
                    {
                        GameObject hint = (GameObject)Instantiate(_hintGameObject, pathParent.transform);
                        float hintPosY = posY - 0.5f;
                        hint.transform.position = new Vector3(posX, hintPosY);
                        hint.transform.Rotate(new Vector3(0, 0, 90));
                        hint.GetComponent<Hint>().SetClueActive(false);

                        _hintVerticalArray[y, x] = hint.GetComponent<Hint>();

                        hint.SetActive(false);
                    }
                }
            }
        }

        ScaleGridAndSetPosition();
    }

    private void ScaleGridAndSetPosition()
    {
        ScaleGrid();
        SetGridAtInitialPosition();
    }

    /// <summary>
    /// Centra el tablero en el espacio de coordenadas.
    /// </summary>
    private void SetGridAtInitialPosition()
    {
        Vector2 startPos = Camera.main.ScreenToWorldPoint(_canvasManager.GetEmptySpaceCenterPosition());
        startPos.y = -startPos.y;
        transform.position = startPos;

        float width = -_boardWidth / 2;

        if (_boardWidth % 2 == 0) width += 0.5f;

        float height = _boardHeight / 2;
        if (_boardHeight % 2 == 0) height -= 0.5f;
        transform.position = new Vector2((transform.position.x + width) * transform.localScale.x,
            (transform.position.y + height) * transform.localScale.y);
    }


    /// <summary>
    /// Escala el tamaño del grid segun el tamaño de la pantalla.
    /// </summary>
    private void ScaleGrid()
    {

        Vector2 emptySize = _canvasManager.GetEmptySpaceSize();
        emptySize.x += Screen.width / 2;
        emptySize.y += Screen.height / 2;
        emptySize = Camera.main.ScreenToWorldPoint(emptySize);

        float widthFactor = emptySize.x / _levelSize[0];
        float heightFactor = emptySize.y / _levelSize[1];
        transform.localScale = new Vector2(widthFactor, heightFactor);
    }

    /// <summary>
    /// Detecta la posicion del raton al ser pulsado y decide si el Tile se puede
    /// marcar como pulsado o no.
    /// </summary>
    private void InputController()
    {


#if !UNITY_EDITOR && (UNITY_ANDROID || UNITY_IOS)

        if (Input.touchCount > 0)
        {
            foreach (Touch touch in Input.touches)
            {
                if (touch.fingerId == 0)
                {
                    if (touch.phase == TouchPhase.Began || touch.phase == TouchPhase.Moved)
                        ProcessClickDown(touch.position);
                    else if (touch.phase == TouchPhase.Ended)
                        ProcessClickUp();
                }
            }
        }

#else

        if (Input.GetMouseButton(0))
        {
            ProcessClickDown(Input.mousePosition);

        }
        else if (Input.GetMouseButtonUp(0))
        {
            ProcessClickUp();
        }
#endif

    }

    private void ProcessClickDown(Vector3 mousePosition)
    {
        mousePosition = Camera.main.ScreenToWorldPoint(mousePosition);
        mousePosition.z = 0;

        _cursorGameObject.SetActive(true);
        _cursorGameObject.transform.position = mousePosition;

        TilePressed((int)Mathf.RoundToInt(mousePosition.x), (int)Mathf.RoundToInt(mousePosition.y));
    }

    private void ProcessClickUp()
    {
        _cursorGameObject.SetActive(false);

        bool finished = true;

        for (int y = 0; y < _boardHeight && finished; y++)
        {
            for (int x = 0; x < _boardWidth && finished; x++)
            {
                if (!_pressedTilesMatrix[y, x])
                    finished = false;
            }
        }

        if (finished)
            GameManager.Instance.LevelFinished();
    }

    /// <summary>
    /// Gestiona la pulsacion del Tile.
    /// 
    /// En el caso de que sea un Tile nuevo comprueba si es adyacente del ultimo Tile pulsado. Ademas, marca
    /// el camino que va siguiendo el jugador.
    /// En el caso de que sea un Tile ya pulsado anteriormente elimina todo el camino posteriormente creado. Ademas,
    /// elimina el camino sobrante.
    /// </summary>
    /// <param name="x">Posicion X en coordenadas del mundo</param>
    /// <param name="y">Posicion Y en coordenadas del mundo</param>
    private void TilePressed(int x, int y)
    {
        y = -y + (_boardHeight / 2);
        x += (_boardWidth / 2);

        if ((x >= 0 && x < _boardWidth && y >= 0 && y < _boardHeight) && _tilesMatrix[y, x] != null) {
            if (!_pressedTilesMatrix[y, x])
            {
                if (IsPeekOfPath(x - 1, y) || IsPeekOfPath(x + 1, y)
                    || IsPeekOfPath(x, y - 1) || IsPeekOfPath(x, y + 1))
                {
                    _pressedTilesMatrix[y, x] = true;
                    _tilesMatrix[y, x].SetPressed(true);

                    if (_pathStack.Peek()._x != x)
                    {
                        _hintHorizontalArray[y, Mathf.Min(_pathStack.Peek()._x, x)].gameObject.SetActive(true);
                    }
                    else if (_pathStack.Peek()._y != y)
                    {
                        _hintVerticalArray[Mathf.Min(_pathStack.Peek()._y, y), x].gameObject.SetActive(true);
                    }

                    _pathStack.Push(new MatrixPos(x, y));
                }
            }
            else
                GoBack(x, y);
        }
    }


    /// <summary>
    /// Retrocede el camino ya realizado por el jugador.
    /// </summary>
    /// <param name="x">Posicion X del tablero a la que se quiere retroceder.</param>
    /// <param name="y">Posicion Y del tablero a la que se quiere retroceder.</param>
    private void GoBack(int x, int y)
    {
        while (_pathStack.Count != 0 && (_pathStack.Peek()._x != x || _pathStack.Peek()._y != y))
        {
            MatrixPos pos = _pathStack.Pop();
            _pressedTilesMatrix[pos._y, pos._x] = false;
            _tilesMatrix[pos._y, pos._x].SetPressed(false);

            if (_pathStack.Peek()._x != pos._x)
            {
                _hintHorizontalArray[_pathStack.Peek()._y, Mathf.Min(_pathStack.Peek()._x, pos._x)].gameObject.SetActive(false);
            }
            else if (_pathStack.Peek()._y != pos._y)
            {
                _hintVerticalArray[Mathf.Min(_pathStack.Peek()._y, pos._y), _pathStack.Peek()._x].gameObject.SetActive(false);
            }
        }
    }

    /// <summary>
    /// Crea el camino cuando el jugador pide una ayuda. La longitud del camino esta determinada por la
    /// variable "_tilesShowingWhenUserWantHint". Cada vez que el jugador requiere ayuda, se sigue
    /// el camino por donde estaba y vuelve a mostrar la misma cantidad de tiles.
    /// </summary>
    public void UserWantHint()
    {
        if (_hintCont < _levelData._path.Count - 1)
        {
            if (_hintCont == 0)
                GoBack(_firsTile._x, _firsTile._y);

            List<int> path = _levelData._path[_hintCont];
            List<int> postPath = _levelData._path[_hintCont + 1];

            if (path[1] != postPath[1])
            {
                _hintHorizontalArray[path[0], Mathf.Min(path[1], postPath[1])].gameObject.SetActive(true);
            }
            else if (path[0] != postPath[0])
            {
                _hintVerticalArray[Mathf.Min(path[0], postPath[0]), path[1]].gameObject.SetActive(true);
            }

            _hintCont++;

            if (_hintCont < _tilesShowing)
            {
                Invoke("UserWantHint", 0.1f);
            }
            else
            {
                _tilesShowing += _tilesShowingWhenUserWantHint;
            }
        }
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="x">Posicion X en coordenadas de matriz</param>
    /// <param name="y">Posicion Y en coordenadas de matriz</param>
    /// <returns>Devuelve true si el Tile en (x, y) fue el ultimo pulsado.</returns>
    private bool IsPeekOfPath(int x, int y)
    {
        return (x >= 0 && x < _boardWidth && y >= 0 && y < _boardHeight)
            && (x == _pathStack.Peek()._x && y == _pathStack.Peek()._y);
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="x">Posicion X en coordenadas de matriz</param>
    /// <param name="y">Posicion Y en coordenadas de matriz</param>
    /// <returns>Devuelve true si el Tile en (x, y) esta pulsado</returns>
    private bool IsTilePressed(int x, int y)
    {
        return (x >= 0 && x < _boardWidth && y >= 0 && y < _boardHeight)
            && (_pressedTilesMatrix[y, x] && _tilesMatrix[y, x] != null);
    }
}
