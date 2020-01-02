using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System;


public class ProgressManager : MonoBehaviour
{
    public static ProgressManager Instance;

    private PersistenceController persistenceController;

    private List<int> levelsCompleted = new List<int>();
    private int _virtualCoin = 55;
    private bool _adsBought = true;
    private float _timeLeftToNextChallenge = 21.0f;



    private void Start()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
            Destroy(gameObject);

        DontDestroyOnLoad(this);

        for (int i = 0; i < GameManager.Instance._categoryLevelFiles.Count; i++)
        {
            levelsCompleted.Add(1);
        }

        persistenceController = new PersistenceController();
        persistenceController.LoadFile();
    }

    public string GetJson(SerializationObject obj)
    {
        return JsonUtility.ToJson(obj);

    }
    public string GetJson(SerializationObjectChiper obj)
    {
        return JsonUtility.ToJson(obj);

    }

    public SerializationObjectChiper GetChiperfromJson(string json)
    {
        return JsonUtility.FromJson<SerializationObjectChiper>(json);
    }


    public int GetUnlockedLevelsOfCategory(int nCategory)
    {
        return levelsCompleted[GameManager.Instance._categoryLevel];
    }

    public void UpdateValues(SerializationObject obj)
    {
        levelsCompleted = obj._levelsCompleted;
        _virtualCoin = obj._virtualCoin;
        _adsBought = obj._adsBought;
        _timeLeftToNextChallenge = obj._timeLeftToNextChallenge;

        Debug.Log("VALORES ACTUALIZADOS: ");
        Debug.Log(levelsCompleted);
        Debug.Log(_virtualCoin);
        Debug.Log(_timeLeftToNextChallenge);

    }

    public void SaveProgress()
    {
        SerializationObject sObj = new SerializationObject(levelsCompleted, _virtualCoin, _adsBought, _timeLeftToNextChallenge);
        persistenceController.SaveFile(sObj);
    }


}
