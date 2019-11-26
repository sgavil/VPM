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
    public int _level;

    public GameObject _tile;

    // Privado
    private LevelsData _levelsData;
    private GridManager _gridManager;

    private int _actualColor { get; set; }

    void Start()
    {

        _levelsData = new LevelsData();
        _gridManager = GetComponent<GridManager>();

        _levelsData.LoadLevelsFromJSON(_categoryLevelFiles);

        CreateGrid(_categoryLevel, _level);
    }

    void SetColor()
    {
        _actualColor = UnityEngine.Random.Range(1, _resourceManager._blockScriptableObjects.Count);
        _tile.GetComponent<Tile>()._defaultTile.sprite = _resourceManager._blockScriptableObjects[0].block;
        _tile.GetComponent<Tile>()._colourTile.sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
    }

    void CreateGrid(string categoryLevel, int level)
    {
        SetColor();
        _gridManager.SetGridSize(_levelsData.GetSize(categoryLevel));
        _gridManager.GenerateGrid(_tile, _levelsData.GetLevelLayout(categoryLevel, level));
    }
}
