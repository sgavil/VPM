using System;
using System.Collections.Generic;

/// <summary>
/// Contiene la información referente a un nivel:
/// Index,Layout y Path
/// </summary>
public class LevelData
{
    public int _index;                  //Indice del nivel
    public int _width;                  //Anchura del nivel
    public int _height;                 //Altura del nivel
    public List<string> _layout;        //Forma que tiene el nivel 
    public List<List<int>> _path;       //Camino que soluciona el nivel

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
 
    /// <summary>
    /// Crea un LevelData a partir de un diccionario 
    /// </summary>
    /// <param name="level">Diccionario a partir del cual se va a crear el leveldata</param>
    public LevelData(Dictionary<string, object> level)
    {
        // Index
        _index = Convert.ToInt32(level["index"]);

        // Layout
        _layout = new List<string>();
        _layout = (level["layout"] as List<object>).ConvertAll(input => input.ToString());
        _height = _layout.Count;
        _width = _layout[0].Length;

        // Path
        List<object> list = (List<object>)level["path"];
        _path = new List<List<int>>();
        for (int i = 0; i < list.Count; i++)
        {
            _path.Add((list[i] as List<object>).ConvertAll(input => Convert.ToInt32(input)));
        }
    }




    
}
