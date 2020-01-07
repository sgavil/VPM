using UnityEngine;
using TMPro;

/// <summary>
/// Controla el canvas que aparece cuando se esta jugando un nivel
/// </summary>
public class LevelCanvasController : MonoBehaviour
{
    [Tooltip("Texto del número del nivel")]
    public TextMeshProUGUI levelNumberText;
    [Tooltip("Objetos con los textos que indican la categoría del nivel")]
    public  GameObject[] categoryLevelIndicator;
    [Tooltip("Texto que contiene el título cuando se juega un reto")]
    public GameObject challengeLevelIndicator;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////



    /// <summary>
    /// Actualiza el texto del nivel actual
    /// </summary>
    public void UpdateLevelText()
    {
        levelNumberText.text = GameManager.Instance._level.ToString();

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    private void Start()
    {
        if (GameManager.Instance._doingChallenge)
        {
            levelNumberText.gameObject.SetActive(false);
            challengeLevelIndicator.SetActive(true);
        }
        else
        {
            categoryLevelIndicator[GameManager.Instance._categoryLevel - 1].SetActive(true);
            levelNumberText.text = GameManager.Instance._level.ToString();
        }
            
    }
   
}
