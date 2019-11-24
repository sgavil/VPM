using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using MiniJSON;
using System.IO;

/// <summary>
/// Categoria de los niveles.
/// </summary>
public enum CategoryLevel
{
    BEGGINER,
    REGULAR,
    ADVANCED,
    EXPERT,
    MASTER
}

///<summary>
///<para>
///Almacena los datos de las categorias/dificultades de niveles y proporciona funcionalidades para poder acceder a esos datos.
///Estas categorias estan compuestas por niveles que tienen:
///</para>
///
///<para>Index: Numero del nivel.</para>
///<para>Layout: Tablero del nivel.</para>
///<para>Path: Camino a seguir para resolver el nivel.</para>
///</summary>
public class LevelsData : MonoBehaviour
{
    // Start is called before the first frame update
    private string _path;
    private string _jsonString;

    public string begginerFilename;
    public string regularFilename;
    public string advancedFilename;
    public string expertFilename;
    public string masterFilename;

    private Dictionary<CategoryLevel, Dictionary<string, object>> categoryLevels;

    /// <summary>
    /// Carga todos los archivos que representan las distintas categorias de niveles.
    /// </summary>
    public void LoadLevelsFromJSON()
    {

        // Begginer
        _path = Application.dataPath + "/Levels/" + begginerFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        AddCategoryLevel(CategoryLevel.BEGGINER, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Regular
        _path = Application.dataPath + "/Levels/" + regularFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        AddCategoryLevel(CategoryLevel.REGULAR, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Advanced
        _path = Application.dataPath + "/Levels/" + advancedFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        AddCategoryLevel(CategoryLevel.ADVANCED, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Expert
        _path = Application.dataPath + "/Levels/" + expertFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        AddCategoryLevel(CategoryLevel.EXPERT, Json.Deserialize(_jsonString) as Dictionary<string, object>);

        // Master
        _path = Application.dataPath + "/Levels/" + masterFilename + ".json";
        _jsonString = File.ReadAllText(_path);
        AddCategoryLevel(CategoryLevel.MASTER, Json.Deserialize(_jsonString) as Dictionary<string, object>);
    }

    /// <summary>
    /// Añade una categoria de niveles al diccionario que las almacena. En el caso de que el diccionario este sin inicializar,
    /// lo inicializa.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="dict">Diccionario que va asociado a esa categoria.</param>
    public void AddCategoryLevel(CategoryLevel category, Dictionary<string, object> dict)
    {
        if (categoryLevels == null)
            categoryLevels = new Dictionary<CategoryLevel, Dictionary<string, object>>();

        categoryLevels.Add(category, dict);
    }

    /// <summary>
    /// Devuelve el diccionario asociado a una determinada categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Diccionario de la categoria.</returns>
    public Dictionary<string, object> GetCategoryLevel(CategoryLevel category)
    {
        return categoryLevels[category];
    }

    /// <summary>
    /// Devuelve el tamaño de los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Tamaño de los niveles.</returns>
    public List<int> GetSize(CategoryLevel category)
    {
        return (GetCategoryLevel(category)["size"] as List<object>).ConvertAll(input => Convert.ToInt32(input));
    }

    /// <summary>
    /// Devuelve el espacio dispoble para los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Espacio disponible.</returns>
    public List<int> GetAvailableSpace(CategoryLevel category)
    {
        return (GetCategoryLevel(category)["availableSpace"] as List<object>).ConvertAll(input => Convert.ToInt32(input));
    }

    /// <summary>
    /// Devuelve los niveles que corresponden a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Niveles</returns>
    public List<object> GetLevels(CategoryLevel category)
    {
        return (List<object>)GetCategoryLevel(category)["level"];
    }

    /// <summary>
    /// Devuelve un solo nivel de todos los correspondientes a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Diccionario con los datos del nivel.</returns>
    public Dictionary<string, object> GetOneLevel(CategoryLevel category, int level)
    {
        return (Dictionary<string, object>)GetLevels(category)[level - 1];
    }

    /// <summary>
    /// Devuelve el valor "index", numero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Numero del nivel.</returns>
    public int GetLevelIndex(CategoryLevel category, int level)
    {
        return Convert.ToInt32(GetOneLevel(category, level)["index"]);
    }

    /// <summary>
    /// Devuelve el valor "layout", tablero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de strings que respresentan las filas del tablero.</returns>
    public List<string> GetLevelLayout(CategoryLevel category, int level)
    {
        return (GetOneLevel(category, level)["layout"] as List<object>).ConvertAll(input => input.ToString());
    }

    /// <summary>
    /// Devuelve el valor "path", camino solucion, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de lista de enteros que representan la sucesion de movimientos para
    /// resolver el tablero.</returns>
    public List<List<int>> GetLevelPath(CategoryLevel category, int level)
    {
        List<object> list = (List<object>)GetOneLevel(category, level)["path"];
        List<List<int>> intList = new List<List<int>>();
        for(int i = 0; i < list.Count; i++)
        {
            intList.Add((list[i] as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }

        return intList;
    }
}