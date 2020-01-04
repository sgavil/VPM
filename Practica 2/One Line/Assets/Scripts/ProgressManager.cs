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

    public int _virtualCoin = 0;
    public bool _adsBought = false;
    public string _serializationVersion = "0.01";
    public int _completedChallenges = 0;
    public DateTime _timeWhenChallengeDone;

    [HideInInspector]
    public string progressID;

    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);

        }
        else
        {
            Destroy(gameObject);
            return;
        }

        for (int i = 0; i < GameManager.Instance._categoryLevelFiles.Count; i++)
        {
            levelsCompleted.Add(0);
        }

        persistenceController = new PersistenceController();

        Debug.Log(Application.persistentDataPath);
        LoadProgress();
    }

    public void LoadProgress()
    {
        persistenceController.LoadFile();
    }
    public void LevelCompleted(int category)
    {
        levelsCompleted[category] += 1;
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
        return levelsCompleted[nCategory - 1] + 1;
    }

    public void UpdateValues(SerializationObject obj)
    {
        levelsCompleted = obj._levelsCompleted;
        _virtualCoin = obj._virtualCoin;
        _adsBought = obj._adsBought;
        _timeWhenChallengeDone = obj.getDate();
        _serializationVersion = obj._serializationVersion;

        _completedChallenges = obj._completedChallenges;

    }

    public void SaveProgress()
    {

        SerializationObject sObj = new SerializationObject(levelsCompleted, _virtualCoin, _adsBought,
             _serializationVersion, _completedChallenges, _timeWhenChallengeDone);

        persistenceController.SaveFile(sObj);
    }
    private void OnApplicationQuit()
    {
        SaveProgress();
    }
    private void OnApplicationFocus(bool focus)
    {
        if (!focus)
            SaveProgress();
    }

    public void UpdateMoneyAdViewed(int n)
    {
        _virtualCoin += n;
        HUDManager.Instance.UpdateMoneyText();

    }
    public void ResetProgress()
    {
        for (int i = 0; i < levelsCompleted.Count; i++)
        {
            levelsCompleted[i] = 0;
        }
        _virtualCoin = 0;
        _adsBought = false;
        
        _completedChallenges = 0;

    }
    public void AddDuplicatedChallengeMoney()
    {
        _virtualCoin += GameManager.Instance._challengeMoneyObtained * 2;
    }
    public void AddChallengeCoins()
    {
        _virtualCoin += GameManager.Instance._challengeMoneyObtained;

    }

}
