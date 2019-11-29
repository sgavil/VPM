using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    // Publico
    public ResourceManager _resourceManager;

    [Tooltip("Archivos .json que almacenan los niveles.")]
    public List<TextAsset> _categoryLevelFiles;

    [Tooltip("Nombre de la categoría del nivel al que quieres acceder.")]
    public string _categoryLevel;

    [Tooltip("Nivel del juego.")]
    public int _level = 0;

    public GameObject _tileGameObject;

    // Privado
    private bool[,] _pressedTilesMatrix;
    private Tile[,] _tilesMatrix;
    private Stack<Tile> _tilesStack;

    private int _boardHeight = 0;
    private int _boardWidth = 0;

    Texture2D _cursorTexture;
    Vector2 _cursorHotspot;

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
    private LevelsData _levelsData;

    private int _actualColor = 0;

    void Start()
    {
        _levelsData = new LevelsData();
        _levelsData.LoadLevelsFromJSON(_categoryLevelFiles);

        SetGridSize(_levelsData.GetSize(_categoryLevel));

        InitializeMatrix();

        CreateGrid(_categoryLevel, _level);
    }

    private void Update()
    {
        InputController();
    }

    /// <summary>
    /// Inicializa las matrices que almacenan los bool y los Tiles. Tambien inicializa la
    /// pila de Tiles.
    /// </summary>
    public void InitializeMatrix()
    {
        _pressedTilesMatrix = new bool[_boardHeight, _boardWidth];
        _tilesMatrix = new Tile[_boardHeight, _boardWidth];
        _tilesStack = new Stack<Tile>();
    }

    /// <summary>
    /// Guarda el tamaño del tablero.
    /// </summary>
    /// <param name="gridSize">Tamaño del tablero</param>
    public void SetGridSize(List<int> gridSize)
    {
        _boardWidth = gridSize[0];
        _boardHeight = gridSize[1];
    }

    /// <summary>
    /// Selecciona los colores por defecto y la piel del Tile y crea el tablero.
    /// </summary>
    /// <param name="categoryLevel">Categoria del nivel para crear</param>
    /// <param name="level">Nivel</param>
    void CreateGrid(string categoryLevel, int level)
    {
        SetTileColor();
        GenerateGrid(_levelsData.GetLevelLayout(categoryLevel, level));
    }

    /// <summary>
    /// Asigna el color por defecto y una piel aleatoria al GameObject Tile.
    /// </summary>
    void SetTileColor()
    {
        Tile tile;
        if (_tileGameObject.TryGetComponent<Tile>(out tile))
        {
            tile._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].block;

            _actualColor = UnityEngine.Random.Range(1, _resourceManager._blockScriptableObjects.Count);
            tile._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
        }

        _cursorTexture = _resourceManager._blockScriptableObjects[_actualColor].touch;
        _cursorHotspot = new Vector2(_cursorTexture.width / 2, _cursorTexture.height / 2);
    }

    /// <summary>
    /// Crea Tiles y los asigna a la matriz de Tiles, ademas modifica la matriz de bool
    /// en la que todos son false excepto el Tile pulsado al inicio y los Tiles que no existen.
    /// Por ultimo, modifica la posicion del tablero para centrarlo en el espacio.
    /// </summary>
    /// <param name="layout">Tablero</param>
    public void GenerateGrid(List<string> layout)
    {
        for (int y = 0; y < _boardHeight; y++)
        {
            for (int x = 0; x < _boardWidth; x++)
            {
                _pressedTilesMatrix[y, x] = true;
                if (layout[y][x] != '0')
                {
                    GameObject tile = (GameObject)Instantiate(_tileGameObject, transform);
                    float posX = x;
                    float posY = -y;

                    tile.transform.position = new Vector2(posX, posY);

                    if (layout[y][x] == '1')
                    {
                        _pressedTilesMatrix[y, x] = false;
                    }
                    else if (layout[y][x] == '2')
                    {
                        _tilesStack.Push(tile.GetComponent<Tile>());
                    }
  
                    _tilesMatrix[y, x] = tile.GetComponent<Tile>();
                    _tilesMatrix[y, x].SetPressed(_pressedTilesMatrix[y, x]);
                }

            }
        }

        SetGridAtInitialPosition();
    }

    /// <summary>
    /// Centra el tablero en el espacio de coordenadas.
    /// </summary>
    private void SetGridAtInitialPosition()
    {
        transform.position = new Vector2(-_boardWidth / 2, _boardHeight / 2);
    }


    /// <summary>
    /// Detecta la posicion del raton al ser pulsado y decide si el Tile se puede
    /// marcar como pulsado o no.
    /// </summary>
    private void InputController()
    {
        if (Input.GetMouseButton(0))
        {
            Cursor.SetCursor(_cursorTexture, _cursorHotspot, CursorMode.ForceSoftware);
            Vector3 mousePosition = Input.mousePosition;
            mousePosition = Camera.main.ScreenToWorldPoint(mousePosition);
            mousePosition.z = 0;
            TilePressed((int)Mathf.RoundToInt(mousePosition.x), (int)Mathf.RoundToInt(mousePosition.y));
        }
        else if (Input.GetMouseButtonUp(0))
        {
            Cursor.SetCursor(null, Vector2.zero, CursorMode.Auto); 
        }
    }

    /// <summary>
    /// Gestiona la pulsacion del Tile.
    /// 
    /// En el caso de que sea un Tile nuevo comprueba si es adyacente del ultimo Tile pulsado.
    /// En el caso de que sea un Tile ya pulsado anteriormente elimina todo el camino posteriormente creado.
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
                if (IsPeekOfTilesPressed(x - 1, y) || IsPeekOfTilesPressed(x + 1, y) 
                    || IsPeekOfTilesPressed(x, y - 1) || IsPeekOfTilesPressed(x, y + 1))
                {
                    _pressedTilesMatrix[y, x] = true;
                    _tilesMatrix[y, x].SetPressed(true);
                    _tilesStack.Push(_tilesMatrix[y, x]);
                }
            }
            else
            {
                while(_tilesStack.Count != 0 && _tilesStack.Peek() != _tilesMatrix[y, x])
                {
                    Tile t = _tilesStack.Pop();
                    Vector2Int tilePos = GetTileMatrixPos(t);
                    _pressedTilesMatrix[tilePos.y, tilePos.x] = false;
                    _tilesMatrix[tilePos.y, tilePos.x].SetPressed(false);
                }
            }
        }
    }

    /// <summary>
    /// Devuelve la posicion del Tile en la matriz.
    /// </summary>
    /// <param name="tile"></param>
    /// <returns>Posicion del Tile</returns>
    private Vector2Int GetTileMatrixPos(Tile tile)
    {
        bool found = false;
        Vector2Int pos = new Vector2Int();
        for (int j = 0; j < _boardHeight && !found; j++)
            for (int i = 0; i < _boardWidth && !found; i++)
                if (_tilesMatrix[j, i] == tile) {
                    found = true;
                    pos.x = i;
                    pos.y = j;
                }
        return pos;
    }

    /// <summary>
    /// 
    /// </summary>
    /// <param name="x">Posicion X en coordenadas de matriz</param>
    /// <param name="y">Posicion Y en coordenadas de matriz</param>
    /// <returns>Devuelve true si el Tile en (x, y) fue el ultimo pulsado.</returns>
    private bool IsPeekOfTilesPressed(int x, int y)
    {
        return (x >= 0 && x < _boardWidth && y >= 0 && y < _boardHeight)
            && _tilesMatrix[y, x] == _tilesStack.Peek();
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
