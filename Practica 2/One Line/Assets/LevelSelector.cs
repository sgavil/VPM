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

    public GameObject[] categoryLevelIndicator;

    private int _categoryLevel;

    private void Start()
    {
        _categoryLevel = GameManager.Instance._categoryLevel;
        GenerateLevels();
        categoryLevelIndicator[_categoryLevel - 1].SetActive(true);
    }

    private void GenerateLevels()
    {
        int unlockedLevels = ProgressManager.Instance.GetUnlockedLevelsOfCategory(_categoryLevel - 1);

        Debug.Log("niel desb: " + unlockedLevels.ToString());
        for (int i = 0; i < GameManager.Instance._levelsGroup._levels[GameManager.Instance._categoryLevel-1].Count; i++)
        {
            if(i< unlockedLevels)      //Creamos botones de desbloqueado
            {
               GameObject unlockedButtonGO =  Instantiate(unlockedImageGO, content.transform);

                string levelText = i.ToString();
                levelText = levelText.PadLeft(3, '0');
                unlockedButtonGO.GetComponentInChildren<Text>().text = levelText;
                unlockedButtonGO.GetComponent<LevelSelectorButton>().levelID = i+1;

            }
            else
            {
                Instantiate(lockedImageGO, content.transform);
            }
           
        }
    }

    public void GoBack()
    {

    }


}
