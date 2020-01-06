using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelSelectorButton : MonoBehaviour
{
    public int levelID;

    public void LevelClicked()
    {
        GameManager.Instance.MoveToLevel(levelID);
    }
}
