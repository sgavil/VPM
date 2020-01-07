using System;
using System.Collections.Generic;
using UnityEngine;
using MiniJSON;


/// <summary>
///Carga y almacena los datos de las categorias/dificultades de niveles y proporciona funcionalidades para poder acceder a esos datos.
/// </summary>
public class LevelsGroup
{
    private List<List<LevelData>> _levels;               //Lista donde se guardaran los niveles
    private List<List<int>> _levelsAvailableSpace;       //Lista para almacenar el tamaño disponible de los niveles
    private List<List<int>> _levelsSize;                 //Lista con los tamaños de los niveles


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
 


    /// <summary>
    /// Carga todos los archivos que representan las distintas categorias de niveles
    /// </summary>
    /// <param name="_categoryLevelFiles">Lista con los archivos que contienen los niveles</param>
    public void LoadLevelsFromJSON(List<TextAsset> _categoryLevelFiles)
    {
        _levels = new List<List<LevelData>>();
        _levelsAvailableSpace = new List<List<int>>();
        _levelsSize = new List<List<int>>();

        for(int i = 0; i < _categoryLevelFiles.Count; i++)
        {
            _levels.Add(new List<LevelData>());
            CreateCategoryLevel(i, (Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>)["level"] as List<object>);
            _levelsAvailableSpace.Add(((Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>)["availableSpace"]
                as List<object>).ConvertAll(input => Convert.ToInt32(input)));
            _levelsSize.Add(((Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>)["size"]
                as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }
    }


  

    /// <summary>
    /// Devuelve el espacio dispoble para los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Espacio disponible.</returns>
    public List<int> GetAvailableSpace(int category)
    {
        return (_levelsAvailableSpace[category]);
    }

    /// <summary>
    /// Devuelve el nivel los datos del nivel elegido por el jugador
    /// </summary>
    /// <param name="categoryLevel">Categoría</param>
    /// <param name="level">Nivel</param>
    /// <returns>Nivel correspondiente a ese numero y categoría</returns>
    public LevelData GetLevel(int categoryLevel,int level)
    {
        return _levels[categoryLevel - 1][level - 1];
    }


    /// <summary>
    /// Devuelve el tamaño del nivel correspondiente a una categoria
    /// </summary>
    /// <param name="categoryLevel">Categoría del nivel</param>
    /// <returns></returns>
    public List<int> GetSize(int categoryLevel)
    {
        return _levelsSize[categoryLevel - 1];
    }

    /// <summary>
    /// Devuelve el número de niveles que contiene una categoria
    /// </summary>
    /// <param name="category">Categoría</param>
    /// <returns>Número de niveles de la categoría</returns>
    public int GetNumberOfLevels(int category)
    {
        return _levels[category - 1].Count;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    /// <summary>
    /// Añade una categoria de niveles al diccionario que las almacena. En el caso de que el diccionario este sin inicializar lo inicializa
    /// </summary>
    /// <param name="category">Categorñia del nivel</param>
    /// <param name="levels">Lista de niveles de la categoría</param>
    private void CreateCategoryLevel(int category, List<object> levels)
    {
        foreach (object o in levels)
        {
            CreateLevel(category, o as Dictionary<string, object>);
        }
    }

    /// <summary>
    /// Crea un nivel y lo añade a su categoría correspondiente
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="dict">Diccionario con la información del nivel</param>
    private void CreateLevel(int category, Dictionary<string, object> dict)
    {
        LevelData levelData = new LevelData(dict);
        _levels[category].Add(levelData);
    }
}