using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el camino no está activo.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando el camino está activo.")]
    public SpriteRenderer _colourTile;

    private bool _pressed = false;
    public bool Pressed
    {
        get
        {
            return _pressed;
        }
        set
        {
            _pressed = value;
            _defaultTile.enabled = !_pressed;
            _colourTile.enabled = _pressed;
        }
    }

    private void OnMouseDown()
    {
        Pressed = true;
    }
}
