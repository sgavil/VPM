using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el camino no está activo.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando el camino está activo.")]
    public SpriteRenderer _colourTile;

    public void SetPressed(bool pressed)
    {
        _defaultTile.enabled = !pressed;
        _colourTile.enabled = pressed;
    }
}
