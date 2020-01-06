using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class LevelSelector : MonoBehaviour
{
    public GameObject lockedImageGO;
    public GameObject unlockedImageGO;




    //Elemento del canvas donde se van a meter las imagenes para hacer el scroll
    public Transform content;
    public Button backButton;

    public GameObject[] categoryLevelIndicator;

    private int _categoryLevel;

    private void Start()
    {
        _categoryLevel = GameManager.Instance._categoryLevel;
        GenerateLevels();
        categoryLevelIndicator[_categoryLevel - 1].SetActive(true);
        backButton.onClick.AddListener(delegate () { GameManager.Instance.GoMainMenu(); });
    }

    private void GenerateLevels()
    {
        int unlockedLevels = ProgressManager.Instance.GetUnlockedLevelsOfCategory(_categoryLevel);

      
        for (int i = 0; i < GameManager.Instance._levelsGroup._levels[_categoryLevel - 1].Count; i++)
        {
            if(i< unlockedLevels)      //Creamos botones de desbloqueado
            {
               GameObject unlockedButtonGO =  Instantiate(unlockedImageGO, content.transform);

                string levelText = (i+1).ToString();
                levelText = levelText.PadLeft(3, '0');
                unlockedButtonGO.GetComponentInChildren<Text>().text = levelText;
                unlockedButtonGO.GetComponent<LevelSelectorButton>().levelID = i + 1;

            }
            else
            {
                Instantiate(lockedImageGO, content.transform);
            }
           
        }
    }

   


}
