using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelManager : MonoBehaviour
{
    // Start is called before the first frame update
    private JsonParser _jsonParser;
    private LevelsData _levelsData;

    void Start()
    {
        _jsonParser = GetComponent<JsonParser>();
        _levelsData = new LevelsData();

        _jsonParser.LoadLevelsFromJson(ref _levelsData);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
