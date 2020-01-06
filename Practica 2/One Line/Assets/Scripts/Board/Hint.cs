using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Hint : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el jugador esta formando el camino.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando se activa una pista.")]
    public SpriteRenderer _colourTile;
    public void SetClueActive(bool clueActive)
    {
        _defaultTile.enabled = !clueActive;
        _colourTile.enabled = clueActive;
    }
}
