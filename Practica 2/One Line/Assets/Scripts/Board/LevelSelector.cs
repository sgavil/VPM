using UnityEngine;
using UnityEngine.UI;

/// <summary>
/// Controla el selector de niveles, instanciando los iconos de nivel de la categoria marcada por el Game Manager (los instancia
/// con una imagen u otra dependiendo de si estan desbloqueados o no).
/// </summary>
public class LevelSelector : MonoBehaviour
{
    [Header("Prefabs iconos de los niveles")]
    [Tooltip("Prefab que se va a instanciar en los niveles que NO hayan sido superados")]
    public GameObject lockedImageGO;

    [Tooltip("Prefab que se va a instanciar en los niveles que SI hayan sido superados")]
    public GameObject unlockedImageGO;

    [Header("Canvas y scroll")]
    [Tooltip("Elemento del canvas donde se van a meter las imagenes para hacer el scroll")]
    public Transform content;

    [Tooltip("Botón para volver al menú principal")]
    public Button backButton;

    [Header("Posibles títulos del selector")]
    [Tooltip("Array de objetos que se mostrarán como título dependiendo de la categoría actual, deben ir en el mismo orden que se han añadido los niveles" +
        "en el Game Manager")]
    public GameObject[] categoryLevelIndicator;

    private int _categoryLevel;         //Guarda la categoría actual definida en el Game Manager



    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    


    private void Start()
    {
        _categoryLevel = GameManager.Instance._categoryLevel;
        GenerateLevels();

        //Activa el título correspondiente a la categoría seleccionada
        categoryLevelIndicator[_categoryLevel - 1].SetActive(true);         

        //Añade al boton de volver atrás su función on click
        backButton.onClick.AddListener(delegate () { GameManager.Instance.GoMainMenu(); }); 
    }

    /// <summary>
    /// Genera los iconos de los niveles que haya en la categoría seleccionada controlando si están desbloqueados o no.
    /// </summary>
    private void GenerateLevels()
    {
        int unlockedLevels = ProgressManager.Instance.GetUnlockedLevelsOfCategory(_categoryLevel);

        for (int i = 0; i < GameManager.Instance._levelsGroup.GetNumberOfLevels(_categoryLevel); i++)
        {
            if(i< unlockedLevels)      //Creamos botones de desbloqueado
            {
               GameObject unlockedButtonGO =  Instantiate(unlockedImageGO, content.transform);

                //Creamos un texto con 3 dígitos rellenos con 0s que indican el nivel que se está creando
                string levelText = (i+1).ToString();
                levelText = levelText.PadLeft(3, '0');

                //Le añadimos al objeto instanciado el título del texto
                unlockedButtonGO.GetComponentInChildren<Text>().text = levelText;
                unlockedButtonGO.GetComponent<LevelSelectorButton>().levelID = i + 1;
            }

            //Creamos botones de bloqueado
            else
                Instantiate(lockedImageGO, content.transform);

        }
    }

   


}
