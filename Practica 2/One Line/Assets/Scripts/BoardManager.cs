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

    private bool[,] _pressedTilesMatrix;
    private Tile[,] _tilesMatrix;

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

    public void InitializeMatrix()
    {
        _pressedTilesMatrix = new bool[_boardHeight, _boardWidth];
        _tilesMatrix = new Tile[_boardHeight, _boardWidth];
    }

    public void SetGridSize(List<int> gridSize)
    {
        _boardWidth = gridSize[0];
        _boardHeight = gridSize[1];
    }

    void CreateGrid(string categoryLevel, int level)
    {
        SetTileColor();
        GenerateGrid(_levelsData.GetLevelLayout(categoryLevel, level));
    }
    void SetTileColor()
    {
        Tile tile;
        if (TryGetComponent<Tile>(out tile))
        {
            tile._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].block;

            _actualColor = UnityEngine.Random.Range(1, _resourceManager._blockScriptableObjects.Count);
            tile._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
        }
    }

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
  
                    _tilesMatrix[y, x] = tile.GetComponent<Tile>();
                    _tilesMatrix[y, x].SetTilePressed(_pressedTilesMatrix[y, x]);
                }

            }
        }

        SetGridAtInitialPosition();
    }

    private void SetGridAtInitialPosition()
    {
        transform.position = new Vector2(-_boardWidth / 2, _boardHeight / 2);
    }





    // INPUT
    private void InputController()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Vector3 mousePosition = Input.mousePosition;
            mousePosition = Camera.main.ScreenToWorldPoint(mousePosition);
            mousePosition.z = 0;
            TilePressed((int)Mathf.RoundToInt(mousePosition.x), (int)Mathf.RoundToInt(mousePosition.y), true);
        }
    }

    private void TilePressed(int x, int y, bool pressed)
    {
        y = -y + (_boardHeight / 2);
        x += (_boardWidth / 2);

        _pressedTilesMatrix[y, x] = pressed;
        _tilesMatrix[y, x].SetTilePressed(pressed);

    }
}
