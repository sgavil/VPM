using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEditor;
using UnityEngine.UI;
using System;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    private const int mainMenuScene = 0;
    private const int levelSelectorSceneIndex = 1;
    private const int levelScene = 2;

    [Header("Archivos de los niveles")]
    [Tooltip("Archivos .json que almacenan los niveles.")]
    public List<TextAsset> _categoryLevelFiles;

    public LevelsGroup _levelsGroup;
    public bool _challengeFailed = false;


    [Header("Atributos del reto")]
    public int _challengeCost;
    public int _challengeMoneyObtained;
    public int _challengeMedalsObtained;
    public int _challengeSeconds;
    public bool _doingChallenge = false;
    public bool _challengeAvailable = true;
    [HideInInspector]
    public float _currChallengeSeconds;

    [HideInInspector]
    public string _challengeLeftTimeText;

    [Tooltip("Tiempo que tiene que esperar el jugador para poder volver a jugar un reto en minutos")]
    public float _challengeTime;
    public float _dailyRewardTime;

    public DateTime _currChallengeDate;
    public DateTime _currDailyRewardDate;

    [Header("Control de nivel y dificultad")]
    [Tooltip("Número de la categoría del nivel al que quieres acceder.")]
    [Range(1, 5)]
    public int _categoryLevel = 0;

    [Tooltip("Nivel del juego.")]
    public int _level = 0;


    [Header("Gestión de moneda virtual")]
    [Tooltip("Precio de una pista.")]
    public int _hintPrice;

    [Tooltip("Dinero obtenido al ver un anuncio.")]
    public int _adsMoneyObtained;
    public int _dailyRewardMoney;

    [HideInInspector]
    public int GameID = 47810; //Game I

    public bool _dailyRewardActive = true;

    private bool _screenSizeIsChanged = false;
    //---------------------------------------------------

    void Awake()
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

        _levelsGroup = new LevelsGroup();
        _levelsGroup.LoadLevelsFromJSON(_categoryLevelFiles);
        DontDestroyOnLoad(this);
        _dailyRewardActive = true;
    }
    public void Start()
    {
        _currChallengeDate = ProgressManager.Instance._timeWhenChallengeDone;
      //  _challengeTime *= 60; //Pasamos el tiempo a segundos

    }
    private void Update()
    {
        Debug.Log("AAAAAAA " + _dailyRewardActive);
        CheckChallengeDate();
        CheckDailyRewardDate();

        UpdateChallengeCounter();
    }

    private void UpdateChallengeCounter()
    {
        if (_doingChallenge)
        {
            _currChallengeSeconds -= Time.deltaTime;
            if (_currChallengeSeconds <= 0)
            {
                _currChallengeSeconds = 0;
                ChallengeFailed();
            }
        }
    }

    private void CheckDailyRewardDate()
    {
        DateTime actualDate = DateTime.Now;
        _currDailyRewardDate = ProgressManager.Instance._timeWhenDailyRewardOpened;
        TimeSpan diff = actualDate - _currDailyRewardDate;
        
        if (diff.TotalHours >= _dailyRewardTime)
        {
            _dailyRewardActive = true;
        }
        
    }

    private void CheckChallengeDate()
    {
        DateTime actualDate = DateTime.Now;
        TimeSpan diff =  actualDate - _currChallengeDate;


        if (diff.TotalMinutes >= _challengeTime)
        {
            _challengeAvailable = true;
            HUDManager.Instance.DisableChallengePanel();
        }
        else
        {
            float time = _challengeTime*60 - (float)diff.TotalSeconds;

            string minutes = Mathf.Floor(time / 60).ToString("00");
            string seconds = (time % 60).ToString("00");
            string fraction = ((time * 100) % 100).ToString("000");
            _challengeLeftTimeText = minutes + ":" + seconds;
        }


    }

   

    public void ChallengeCompleted(bool duplicateCoins)
    {
        
        ProgressManager.Instance._completedChallenges++;
        if(!duplicateCoins)
            ProgressManager.Instance.AddMoney(_challengeMoneyObtained);
        
        _doingChallenge = false;
        GoMainMenu();
    }
    public void ChallengeFailed()
    {
        _doingChallenge = false;
        HUDManager.Instance.ShowFailedChallengeCanvas();
        _challengeFailed = true;

    }
    public void SetScreenSizeIsChanged(bool b)
    {
        _screenSizeIsChanged = b;
    }

    public bool IsScreenSizeChanged()
    {
        return _screenSizeIsChanged;
    }

    /// <summary>
    /// Devuelve el nivel los datos del nivel elegido por el jugador
    /// </summary>
    /// <returns>Datos del nivel</returns>
    public LevelData GetLevel()
    {
        return _levelsGroup._levels[_categoryLevel - 1][_level - 1];
    }

    public List<int> GetAvailableSpace()
    {
        return _levelsGroup._levelsAvailableSpace[_categoryLevel - 1];
    }

    public List<int> GetSize()
    {
        return _levelsGroup._levelsSize[_categoryLevel - 1];
    }

    public void AddSkippedWhileChallenge()
    {
        _doingChallenge = false;
        GoMainMenu();
    }
    public bool PlayChallenge(bool adShown)
    {
        if (!adShown && _challengeCost > ProgressManager.Instance._virtualCoin )
            return false;

        if (!adShown)
            ProgressManager.Instance._virtualCoin -= _challengeCost;

        _challengeFailed = false;
        ProgressManager.Instance._timeWhenChallengeDone = DateTime.Now;
        _currChallengeDate = DateTime.Now;
        _challengeAvailable = false;
        _currChallengeSeconds = _challengeSeconds;
        _doingChallenge = true;


        int category = UnityEngine.Random.Range(1,_categoryLevelFiles.Count);
        int number = UnityEngine.Random.Range(1, _levelsGroup._levels[category].Count);
        _categoryLevel = category;
        MoveToLevel(number);
        return true;
    }



    public void NextLevel()
    {
        _level++;
        if (_level > _levelsGroup._levels[_categoryLevel].Count)
        {
            _categoryLevel++;
            _level = 0;
        }
    }

    [HideInInspector]
    public string gameID;

    public void ClickBack()
    {
        SoundManager.Instance.PlayBack();
        if (_doingChallenge)
        {
            _doingChallenge = false;
            GoMainMenu();
        }
            
        else
            LoadLevelSelector(_categoryLevel);
    }
    public void ClickHint()
    {
        if (ProgressManager.Instance._virtualCoin >= _hintPrice)
        {
            FindObjectOfType<BoardManager>().UserWantHint();
            ProgressManager.Instance._virtualCoin -= _hintPrice;
            HUDManager.Instance.UpdateMoneyText();

        }
        else
        {
            SoundManager.Instance.PlayNotEnoughMoney();
            StartCoroutine(HUDManager.Instance.ShowNotEnoughMoneyText());
        }
    }
    
    public void ClickReplay()
    {
        SoundManager.Instance.PlayReplay();
        FindObjectOfType<BoardManager>().Replay();
    }

    public void LoadLevelSelector(int i)
    {
        _categoryLevel = i;
        SceneManager.LoadScene(levelSelectorSceneIndex, LoadSceneMode.Single);
    }

    public void GoMainMenu()
    {
        SceneManager.LoadScene(mainMenuScene, LoadSceneMode.Single);
    }
    public void MoveToLevel(int number)
    {
        _level = number;
        SceneManager.LoadScene(levelScene, LoadSceneMode.Single);
    }
    
    

}
