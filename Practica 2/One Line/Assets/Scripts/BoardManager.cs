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

    // Privado
    private bool[,] _pressedTilesMatrix;
    private Tile[,] _tilesMatrix;
    private Stack<MatrixPos> _pathStack;

    private int _boardHeight = 0;
    private int _boardWidth = 0;

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

    private int _actualColor = 0;

    void Start()
    {

        _levelData = GameManager.Instance.GetLevel();

        SetGridSize();

        InitializeMatrix();

        CreateGrid();
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
        SetTileColor();
        GenerateGrid();
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
    }

    /// <summary>
    /// Crea Tiles y los asigna a la matriz de Tiles, ademas modifica la matriz de bool
    /// en la que todos son false excepto el Tile pulsado al inicio y los Tiles que no existen.
    /// Por ultimo, modifica la posicion del tablero para centrarlo en el espacio.
    /// </summary>
    /// <param name="layout">Tablero</param>
    public void GenerateGrid()
    {
        for (int y = 0; y < _boardHeight; y++)
        {
            for (int x = 0; x < _boardWidth; x++)
            {
                _pressedTilesMatrix[y, x] = true;
                if (_levelData._layout[y][x] != '0')
                {
                    GameObject tile = (GameObject)Instantiate(_tileGameObject, transform);
                    float posX = x;
                    float posY = -y;

                    tile.transform.position = new Vector2(posX, posY);

                    if (_levelData._layout[y][x] == '1')
                    {
                        _pressedTilesMatrix[y, x] = false;
                    }
                    else if (_levelData._layout[y][x] == '2')
                    {
                        _pathStack.Push(new MatrixPos(x, y));
                    }
  
                    _tilesMatrix[y, x] = tile.GetComponent<Tile>();
                    _tilesMatrix[y, x].SetPressed(_pressedTilesMatrix[y, x]);
                }

            }
        }

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
        transform.position = new Vector2(transform.position.x + width, transform.position.y + height);
    }


    /// <summary>
    /// Escala el tamaño del grid segun el tamaño de la pantalla.
    /// </summary>
    private void ScaleGrid()
    {
        
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
        TilePressed((int)Mathf.RoundToInt(mousePosition.x), (int)Mathf.RoundToInt(mousePosition.y));
    }

    private void ProcessClickUp()
    {

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
                if (IsPeekOfPath(x - 1, y) || IsPeekOfPath(x + 1, y) 
                    || IsPeekOfPath(x, y - 1) || IsPeekOfPath(x, y + 1))
                {
                    _pressedTilesMatrix[y, x] = true;
                    _tilesMatrix[y, x].SetPressed(true);
                    _pathStack.Push(new MatrixPos(x, y));
                }
            }
            else
            {
                while(_pathStack.Count != 0 && (_pathStack.Peek()._x != x || _pathStack.Peek()._y != y))
                {
                    MatrixPos pos = _pathStack.Pop();
                    _pressedTilesMatrix[pos._y, pos._x] = false;
                    _tilesMatrix[pos._y, pos._x].SetPressed(false);
                }
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



/*

void Update(){
    
    if (estoyEnPC){
        if(Input.GetMouseButton(0)){
            .........
        }
        else if (Input.GetMouseButtonUp(0){
            procesarLiberacion(); // para probar si hemos acabado el nivel o no
        }
    }
    else{
        if (touchCount > 0){
            Buscar el dedo que te interesa dentro de "touches" (0) y procesar.
        }
    }

}
     
*/