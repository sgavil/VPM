using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(GridManager))]
public class LevelManager : MonoBehaviour
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
    private GridManager _gridManager;

    private int _actualColor = 0;

    void Start()
    {
        _levelsData = new LevelsData();
        _levelsData.LoadLevelsFromJSON(_categoryLevelFiles);

        _gridManager = GetComponent<GridManager>();
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

        _gridManager.SetGridSize(_levelsData.GetSize(categoryLevel));
        _gridManager.GenerateGrid(_tile, _levelsData.GetLevelLayout(categoryLevel, level));
    }
}
