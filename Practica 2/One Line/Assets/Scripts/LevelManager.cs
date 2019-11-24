using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(LevelsData))]
public class LevelManager : MonoBehaviour
{
    // Start is called before the first frame update
    private LevelsData _levelsData;
    private GridManager _gridManager;

    public
        ResourceManager _resourceManager;

    private int _actualColor { get; set; }

    void Start()
    {

        _resourceManager = GameObject.Find("ResourceManager").GetComponent<ResourceManager>();

        _levelsData = GetComponent<LevelsData>();
        _levelsData.LoadLevelsFromJSON();


        _gridManager = GetComponent<GridManager>();

        _actualColor = UnityEngine.Random.Range(0, _resourceManager._blockScriptableObjects.Count);
        _gridManager.SetActualColor(_actualColor);

        _gridManager.SetGridSize(_levelsData.GetSize(CategoryLevel.MASTER));
        _gridManager.GenerateGrid(_levelsData.GetLevelLayout(CategoryLevel.MASTER, 1));

    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
