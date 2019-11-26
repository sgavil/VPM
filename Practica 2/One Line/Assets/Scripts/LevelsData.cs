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
public class LevelsData
{
    private Dictionary<string, Dictionary<string, object>> categoryLevels;

    /// <summary>
    /// Carga todos los archivos que representan las distintas categorias de niveles.
    /// </summary>
    public void LoadLevelsFromJSON(List<TextAsset> _categoryLevelFiles)
    {
        for(int i = 0; i < _categoryLevelFiles.Count; i++)
        {
            AddCategoryLevel(_categoryLevelFiles[i].name.ToUpper(), Json.Deserialize(_categoryLevelFiles[i].text) as Dictionary<string, object>);
        }
    }

    /// <summary>
    /// Añade una categoria de niveles al diccionario que las almacena. En el caso de que el diccionario este sin inicializar,
    /// lo inicializa.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="dict">Diccionario que va asociado a esa categoria.</param>
    public void AddCategoryLevel(string category, Dictionary<string, object> dict)
    {
        if (categoryLevels == null)
            categoryLevels = new Dictionary<string, Dictionary<string, object>>();

        categoryLevels.Add(category.ToUpper(), dict);
    }

    /// <summary>
    /// Devuelve el diccionario asociado a una determinada categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Diccionario de la categoria.</returns>
    public Dictionary<string, object> GetCategoryLevel(string category)
    {
        return categoryLevels[category.ToUpper()];
    }

    /// <summary>
    /// Devuelve el tamaño de los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Tamaño de los niveles.</returns>
    public List<int> GetSize(string category)
    {
        return (GetCategoryLevel(category.ToUpper())["size"] as List<object>).ConvertAll(input => Convert.ToInt32(input));
    }

    /// <summary>
    /// Devuelve el espacio dispoble para los niveles de una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Espacio disponible.</returns>
    public List<int> GetAvailableSpace(string category)
    {
        return (GetCategoryLevel(category.ToUpper())["availableSpace"] as List<object>).ConvertAll(input => Convert.ToInt32(input));
    }

    /// <summary>
    /// Devuelve los niveles que corresponden a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns>Niveles</returns>
    public List<object> GetLevels(string category)
    {
        return (List<object>)GetCategoryLevel(category.ToUpper())["level"];
    }

    /// <summary>
    /// Devuelve un solo nivel de todos los correspondientes a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Diccionario con los datos del nivel.</returns>
    public Dictionary<string, object> GetOneLevel(string category, int level)
    {
        return (Dictionary<string, object>)GetLevels(category.ToUpper())[level - 1];
    }

    /// <summary>
    /// Devuelve el valor "index", numero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Numero del nivel.</returns>
    public int GetLevelIndex(string category, int level)
    {
        return Convert.ToInt32(GetOneLevel(category.ToUpper(), level)["index"]);
    }

    /// <summary>
    /// Devuelve el valor "layout", tablero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de strings que respresentan las filas del tablero.</returns>
    public List<string> GetLevelLayout(string category, int level)
    {
        return (GetOneLevel(category.ToUpper(), level)["layout"] as List<object>).ConvertAll(input => input.ToString());
    }

    /// <summary>
    /// Devuelve el valor "path", camino solucion, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de lista de enteros que representan la sucesion de movimientos para
    /// resolver el tablero.</returns>
    public List<List<int>> GetLevelPath(string category, int level)
    {
        List<object> list = (List<object>)GetOneLevel(category.ToUpper(), level)["path"];
        List<List<int>> intList = new List<List<int>>();
        for(int i = 0; i < list.Count; i++)
        {
            intList.Add((list[i] as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }

        return intList;
    }
}