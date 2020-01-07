using UnityEngine;

/// <summary>
/// Clase que controla la activación/desactivación de las ayudas para completar el camino
/// </summary>
public class Hint : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el jugador esta formando el camino.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando se activa una pista.")]
    public SpriteRenderer _colourTile;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    /// <summary>
    /// Cambia el estado de activación del hint
    /// </summary>
    /// <param name="clueActive">Controla si cambia a activo o inactivo</param>
    public void SetClueActive(bool clueActive)
    {
        _defaultTile.enabled = !clueActive;
        _colourTile.enabled = clueActive;
    }
}
