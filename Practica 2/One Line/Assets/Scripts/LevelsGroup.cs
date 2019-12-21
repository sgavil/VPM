using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using MiniJSON;
using System.IO;


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
public class LevelsGroup
{
    public List<List<int>> _levelsAvailableSpace;
    public List<List<LevelData>> _levels;
    /*public List<LevelData> _begginerLevels;
    public List<LevelData> _regularLevels;
    public List<LevelData> _advancedLevels;
    public List<LevelData> _expertLevels;
    public List<LevelData> _masterLevels;*/

    /// <summary>
    /// Carga todos los archivos que representan las distintas categorias de niveles.
    /// </summary>
    public void LoadLevelsFromJSON(List<TextAsset> _categoryLevelFiles)
    {
        _levels = new List<List<LevelData>>();
        _levelsAvailableSpace = new List<List<int>>();
        for(int i = 0; i < _categoryLevelFiles.Count; i++)
        {
            _levels.Add(new List<LevelData>());
            CreateCategoryLevel(i, (Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>)["level"] as List<object>);
            _levelsAvailableSpace.Add(((Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>)["availableSpace"]
                as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }
    }

    /// <summary>
    /// Añade una categoria de niveles al diccionario que las almacena. En el caso de que el diccionario este sin inicializar,
    /// lo inicializa.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="dict">Diccionario que va asociado a esa categoria.</param>
    public void CreateCategoryLevel(int category, List<object> levels)
    {
        foreach(object o in levels)
        {
            CreateLevel(category, o as Dictionary<string, object>);
        }
    }

    /// <summary>
    /// Crea un nivel y lo añade a su categoria correspondiente.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    public void CreateLevel(int category, Dictionary<string, object> dict)
    {
        LevelData levelData = new LevelData(dict);
        _levels[category].Add(levelData);
    }

    /// <summary>
    /// Devuelve el tamaño de los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Tamaño de los niveles.</returns>
    /*public List<int> GetSize(string category)
    {
        return (GetCategoryLevel(category.ToUpper())["size"] as List<object>).ConvertAll(input => Convert.ToInt32(input));
    }*/

    /// <summary>
    /// Devuelve el espacio dispoble para los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Espacio disponible.</returns>
    public List<int> GetAvailableSpace(int category)
    {
        return (_levelsAvailableSpace[category]);
    }
}