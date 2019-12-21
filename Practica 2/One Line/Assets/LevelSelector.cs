using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelSelector : MonoBehaviour
{
    public GameObject lockedImageGO;

    private void Start()
    {
        for (int i = 0; i < GameManager.Instance._levelsGroup._levels[GameManager.Instance._categoryLevel].Count; i++)
        {
            Instantiate(lockedImageGO, transform);
        }
    }
}
