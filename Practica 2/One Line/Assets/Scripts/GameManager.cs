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


    [Header("Atributos del reto")]
    public int _challengeCost;
    public int _challengeMoneyObtained;
    public int _challengeMedalsObtained;
    public int _challengeSeconds;
    public bool _doingChallenge = false;
    public bool _challengeAvailable = true;
    

    [HideInInspector]
    public string _challengeLeftTimeText;

    [Tooltip("Tiempo que tiene que esperar el jugador para poder volver a jugar un reto en minutos")]
    public float _challengeTime;
    public DateTime _currChallengeDate;

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

    [HideInInspector]
    public int GameID = 47810; //Game I

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
    }
    public void Start()
    {
        _currChallengeDate = ProgressManager.Instance._timeWhenChallengeDone;
        _challengeTime *= 60; //Pasamos el tiempo a segundos

    }
    private void Update()
    {
        CheckChallengeDate();
    }
    private void CheckChallengeDate()
    {
        DateTime actualDate = DateTime.Now;
        TimeSpan diff =  actualDate - _currChallengeDate;


        if (diff.TotalSeconds >= _challengeTime)
        {
            _challengeAvailable = true;
            HUDManager.Instance.DisableChallengePanel();
        }
        else
        {
            float time = _challengeTime - (float)diff.TotalSeconds;

            string minutes = Mathf.Floor(time / 60).ToString("00");
            string seconds = (time % 60).ToString("00");
            string fraction = ((time * 100) % 100).ToString("000");
            _challengeLeftTimeText = minutes + ":" + seconds.ToString();
            Debug.Log(_challengeLeftTimeText);
        }


    }

    public void ChallengeCompleted()
    {
        ProgressManager.Instance._completedChallenges++;
        ProgressManager.Instance.AddChallengeCoins();
        
        _doingChallenge = false;
        GoMainMenu();
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

        ProgressManager.Instance._timeWhenChallengeDone = DateTime.Now;
        _currChallengeDate = DateTime.Now;
        _challengeAvailable = false;

        int category = UnityEngine.Random.Range(1,_categoryLevelFiles.Count);
        int number = UnityEngine.Random.Range(1, _levelsGroup._levels[category].Count);
        _categoryLevel = category;
       _doingChallenge = true;
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
        //SceneManager.LoadScene(levelScene);
    }
    [HideInInspector]
    public string gameID;
    // BOTONES

    public void ClickBack()
    {
        SoundManager.Instance.PlayBack();
        if (_doingChallenge)
            GoMainMenu();
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
        Debug.Log("me he cambiado al selector de niveles " + i.ToString());
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
        Debug.Log("El number es: " + number);
    }
    
    

}
