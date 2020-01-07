using UnityEngine;

/// <summary>
/// Componente añadido a un boton para que pueda seleccionar a que nivel se mueve
/// </summary>
public class LevelSelectorButton : MonoBehaviour
{
    public int levelID;

    public void LevelClicked()
    {
        GameManager.Instance.MoveToLevel(levelID);
    }
}
