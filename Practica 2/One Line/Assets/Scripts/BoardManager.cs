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

    public GameObject _tile;

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

        CreateGrid(_categoryLevel, _level);
    }

    void SetTileColor()
    {
        _actualColor = UnityEngine.Random.Range(1, _resourceManager._blockScriptableObjects.Count);
        _tile.GetComponent<Tile>()._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].block;
        _tile.GetComponent<Tile>()._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
    }

    void CreateGrid(string categoryLevel, int level)
    {
        SetTileColor();

        SetGridSize(_levelsData.GetSize(categoryLevel));
        GenerateGrid(_tile, _levelsData.GetLevelLayout(categoryLevel, level));
    }

    public void SetGridSize(List<int> gridSize)
    {
        _boardWidth = gridSize[0];
        _boardHeight = gridSize[1];
    }
    private void SetGridAtInitialPosition()
    {
        transform.position = new Vector2(-_boardWidth / 2, _boardHeight / 2);
    }

    public void GenerateGrid(GameObject referenceTile, List<string> layout)
    {
        for (int y = 0; y < layout.Count; y++)
        {
            for (int x = 0; x < layout[y].Length; x++)
            {
                if (layout[y][x] != '0')
                {
                    GameObject tile = (GameObject)Instantiate(referenceTile, transform);
                    float posX = x;
                    float posY = -y;

                    tile.transform.position = new Vector2(posX, posY);

                    if (layout[y][x] == '1')
                        tile.GetComponent<Tile>().Pressed = false;
                    else if (layout[y][x] == '2')
                        tile.GetComponent<Tile>().Pressed = true;
                }

            }
        }

        SetGridAtInitialPosition();
    }
}
