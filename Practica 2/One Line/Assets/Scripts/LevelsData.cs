using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

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
public class LevelsData
{
    
    private Dictionary<CategoryLevel, Dictionary<string, object>> categoryLevels;

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
    public Dictionary<string, object> getCategoryLevel(CategoryLevel category)
    {
        return categoryLevels[category];
    }

    /// <summary>
    /// Devuelve los niveles que corresponden a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <returns></returns>
    public List<object> getLevels(CategoryLevel category)
    {
        return (List<object>)getCategoryLevel(category)["level"];
    }

    /// <summary>
    /// Devuelve un solo nivel de todos los correspondientes a una categoria.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Diccionario con los datos del nivel.</returns>
    public Dictionary<string, object> getOneLevel(CategoryLevel category, int level)
    {
        return (Dictionary<string, object>)getLevels(category)[level - 1];
    }

    /// <summary>
    /// Devuelve el valor "index", numero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Numero del nivel.</returns>
    public int getLevelIndex(CategoryLevel category, int level)
    {
        return Convert.ToInt32(getOneLevel(category, level)["index"]);
    }

    /// <summary>
    /// Devuelve el valor "layout", tablero del nivel, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de strings que respresentan las filas del tablero.</returns>
    public List<string> getLevelLayout(CategoryLevel category, int level)
    {
        return (getOneLevel(category, level)["layout"] as List<object>).ConvertAll(input => input.ToString());
    }

    /// <summary>
    /// Devuelve el valor "path", camino solucion, de un nivel.
    /// </summary>
    /// <param name="category">Categoria del nivel.</param>
    /// <param name="level">Nivel al que se quiere acceder.</param>
    /// <returns>Lista de lista de enteros que representan la sucesion de movimientos para
    /// resolver el tablero.</returns>
    public List<List<int>> getLevelPath(CategoryLevel category, int level)
    {
        List<object> list = (List<object>)getOneLevel(category, level)["path"];
        List<List<int>> intList = new List<List<int>>();
        for(int i = 0; i < list.Count; i++)
        {
            intList.Add((list[i] as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }

        return intList;
    }
}