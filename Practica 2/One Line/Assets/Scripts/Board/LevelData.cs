using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelData
{
    public LevelData() { }
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

    public int _index;
    public int _width;
    public int _height;
    public List<string> _layout;
    public List<List<int>> _path;
}
