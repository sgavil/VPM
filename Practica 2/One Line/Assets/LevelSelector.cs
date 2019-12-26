using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelSelector : MonoBehaviour
{
    public GameObject lockedImageGO;


    //Elemento del canvas donde se van a meter las imagenes para hacer el scroll
    public Transform content;

    public GameObject[] categoryLevelIndicator;

    private void Start()
    {
        GenerateLevels();
        categoryLevelIndicator[GameManager.Instance._categoryLevel - 1].SetActive(true);
    }

    private void GenerateLevels()
    {
        for (int i = 0; i < GameManager.Instance._levelsGroup._levels[GameManager.Instance._categoryLevel-1].Count; i++)
        {
            Instantiate(lockedImageGO, content.transform);
        }
    }

    public void GoBack()
    {

    }
}
