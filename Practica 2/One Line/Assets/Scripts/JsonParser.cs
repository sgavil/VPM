using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using MiniJSON;


public class JsonParser : MonoBehaviour
{
    // Start is called before the first frame update
    private string _path;
    private string _jsonString;

    public string begginerFilename;
    public string regularFilename;
    public string advancedFilename;
    public string expertFilename;
    public string masterFilename;

    /// <summary>
    /// Carga todos los archivos que representan las distintas categorias de niveles.
    /// </summary>
    /// <param name="levelsData">Clase LevelsData donde se almacenaran los datos de los niveles.</param>
    public void LoadLevelsFromJson(ref LevelsData levelsData)
    {

        // Begginer
        _path = Application.dataPath + "/Levels/" + begginerFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        levelsData.AddCategoryLevel(CategoryLevel.BEGGINER, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Regular
        _path = Application.dataPath + "/Levels/" + regularFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        levelsData.AddCategoryLevel(CategoryLevel.REGULAR, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Advanced
        _path = Application.dataPath + "/Levels/" + advancedFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        levelsData.AddCategoryLevel(CategoryLevel.ADVANCED, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Expert
        _path = Application.dataPath + "/Levels/" + expertFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        levelsData.AddCategoryLevel(CategoryLevel.EXPERT, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Master
        _path = Application.dataPath + "/Levels/" + masterFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        levelsData.AddCategoryLevel(CategoryLevel.MASTER, Json.Deserialize(_jsonString) as Dictionary<string, object>);
    }
}
