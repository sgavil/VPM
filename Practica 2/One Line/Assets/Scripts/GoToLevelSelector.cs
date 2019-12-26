using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GoToLevelSelector : MonoBehaviour
{
    [Tooltip("Dificultad del nivel ")]
    public int categoryLevel = 0;


    public void GoToSelector()
    {
        GameManager.Instance.LoadLevelSelector(categoryLevel);
    }
}
