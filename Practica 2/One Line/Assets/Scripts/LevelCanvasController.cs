using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelCanvasController : MonoBehaviour
{
    public Text levelNumberText;
    public  GameObject[] categoryLevelIndicator;
    public GameObject challengeLevelIndicator;


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
    public void UpdateLevelText()
    {
        levelNumberText.text = GameManager.Instance._level.ToString();

    }

}
