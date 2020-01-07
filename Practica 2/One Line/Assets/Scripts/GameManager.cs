using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using System;

/// <summary>
/// Manager del juego, posee los valores como dinero que se añade al realizar acciones, tiempos de enfriamiento de las recompensas,
/// nivel actual, categoría actual además de permitir realizar otras acciones como el paso de nivel.
/// </summary>
public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    ///Índices de las escenas del juego
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

    [Tooltip("Control de si se ha fallado el reto")]
    public bool _challengeFailed = false;

    [HideInInspector]
    public float _currChallengeSeconds;

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
    [Tooltip("Dinero obtenido al recoger una recompensa diaria")]
    public int _dailyRewardMoney;

    [Header("Recompensa diaria")]
    [Tooltip("Controla si la recompensa está disponible")]
    public bool _dailyRewardActive;
    [Tooltip("Tiempo de espera de la recompensa diaria")]
    public float _dailyRewardTime;
    public DateTime _currDailyRewardDate;

    [HideInInspector]
    public int GameID = 47810; //Game ID

    private bool _screenSizeIsChanged = false;

    private BoardManager _boardManager;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /// <summary>
    /// Llamado cuando se ha completado un reto
    /// </summary>
    /// <param name="duplicateCoins">Indica si se ha visto un anuncio para duplicar las monedas obtenidas</param>
    public void ChallengeCompleted(bool duplicateCoins)
    {

        ProgressManager.Instance._completedChallenges++;
        if (!duplicateCoins)
            ProgressManager.Instance.AddMoney(_challengeMoneyObtained);

        _doingChallenge = false;
        GoMainMenu();
    }



    /// <summary>
    /// Modifica el booleano de control de si se ha cambiado el tamaño de la ventana
    /// </summary>
    /// <param name="b"></param>
    public void SetScreenSizeIsChanged(bool b)
    {
        _screenSizeIsChanged = b;
    }

    /// <summary>
    /// Devuelve si ya se ha escalado la pantalla
    /// </summary>
    /// <returns></returns>
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
        return _levelsGroup.GetLevel(_categoryLevel, _level);
    }

    /// <summary>
    /// Devuelve el tamaño disponible
    /// </summary>
    /// <returns></returns>
    public List<int> GetAvailableSpace()
    {
        return _levelsGroup.GetAvailableSpace(_categoryLevel);
    }

    /// <summary>
    /// Devuelve el tamaño de una categoria
    /// </summary>
    /// <returns></returns>
    public List<int> GetSize()
    {
        return _levelsGroup.GetSize(_categoryLevel);
    }

    /// <summary>
    /// Ocurre cuando se ha intentado jugar un reto viendo un video y el usuario lo ha saltado
    /// </summary>
    public void AddSkippedWhileChallenge()
    {
        _doingChallenge = false;
        GoMainMenu();
    }

    /// <summary>
    /// Carga el juego de un reto, teniendo en cuenta si se ha entrado al reto gastando monedas o viendo un video
    /// </summary>
    /// <param name="adShown">Se ha visto un video para entrar al reto en lugar de pagar</param>
    /// <returns></returns>
    public bool PlayChallenge(bool adShown)
    {
        if (!adShown && _challengeCost > ProgressManager.Instance._virtualCoin)
            return false;

        if (!adShown)
            ProgressManager.Instance._virtualCoin -= _challengeCost;

        _challengeFailed = false;
        ProgressManager.Instance._timeWhenChallengeDone = DateTime.Now;
        _currChallengeDate = DateTime.Now;
        _challengeAvailable = false;
        _currChallengeSeconds = _challengeSeconds;
        _doingChallenge = true;


        int category = UnityEngine.Random.Range(1, _categoryLevelFiles.Count);
        int number = UnityEngine.Random.Range(1, _levelsGroup.GetNumberOfLevels(_categoryLevel));
        _categoryLevel = category;
        MoveToLevel(number);
        return true;
    }


    /// <summary>
    /// Aumenta el contador del nivel, si ha completado todos los de la categoria pasa a la siguiente categoria
    /// </summary>
    public void NextLevel()
    {
        _level++;
        if (_level > _levelsGroup.GetNumberOfLevels(_categoryLevel))
        {
            _categoryLevel++;
            _level = 0;
        }
    }

    [HideInInspector]
    public string gameID;

    /// <summary>
    /// Vuelve a la pantalla anterior
    /// </summary>
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

    /// <summary>
    /// Gestiona la petición de ayuda del usuario , si tiene dinero para pagarlo muestra parte de la solucion,
    /// si no, muestra un texto que indica que falta dinero.
    /// </summary>
    public void ClickHint()
    {
        if (ProgressManager.Instance._virtualCoin >= _hintPrice)
        {
            if (_boardManager == null)
                _boardManager = FindObjectOfType<BoardManager>();
            if (_boardManager.AllHintsShowed())
                return;

            _boardManager.UserWantHint();
            ProgressManager.Instance._virtualCoin -= _hintPrice;
            HUDManager.Instance.UpdateMoneyText();

        }
        else
        {
            SoundManager.Instance.PlayNotEnoughMoney();
            StartCoroutine(HUDManager.Instance.ShowNotEnoughMoneyText());
        }
    }

    /// <summary>
    /// Llama al replay del tablero para deshacer todos los tiles marcados
    /// </summary>
    public void ClickReplay()
    {
        SoundManager.Instance.PlayReplay();
        if (_boardManager == null)
            FindObjectOfType<BoardManager>();
        _boardManager.Replay();
    }

    /// <summary>
    /// Carga la escena del selector de niveles de una categoría espécifica
    /// </summary>
    /// <param name="i">Categoría que se quiere cargar</param>
    public void LoadLevelSelector(int i)
    {
        _categoryLevel = i;
        SceneManager.LoadScene(levelSelectorSceneIndex, LoadSceneMode.Single);
    }


    /// <summary>
    /// Carga la escena del menu principal 
    /// </summary>
    public void GoMainMenu()
    {
        SceneManager.LoadScene(mainMenuScene, LoadSceneMode.Single);
    }
    /// <summary>
    /// Carga la escena del nivel 
    /// </summary>
    /// <param name="number">Nivel que se carga</param>
    public void MoveToLevel(int number)
    {
        _level = number;
        SceneManager.LoadScene(levelScene, LoadSceneMode.Single);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


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

        _levelsGroup = new LevelsGroup();
        _levelsGroup.LoadLevelsFromJSON(_categoryLevelFiles);
        DontDestroyOnLoad(this);
    }
    private void Start()
    {
        _currChallengeDate = ProgressManager.Instance._timeWhenChallengeDone;

    }
    private void Update()
    {
        CheckChallengeDate();
        CheckDailyRewardDate();

        UpdateChallengeCounter();
    }
    /// <summary>
    /// Actualiza la cuenta atrás del reto, si esta llega a cero se tomará como un reto fallido
    /// </summary>
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
    /// <summary>
    /// Comprueba la diferencia entre el tiempo actual y la fecha de la última recompensa diaria recogida
    /// si se ha superado el tiempo de espera para la siguiente recompensa se permite al jugador volver a recogerla
    /// </summary>
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
    /// <summary>
    /// Comprueba la diferencia entre el tiempo actual y la ultima fecha recogida de un reto
    /// Si se ha superado el tiempo de espera permite al jugador volver a jugar otro reto, en caso contrario,
    /// muestra un texto con una cuenta atrás
    /// </summary>
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

    /// <summary>
    /// Llamado cuando se ha fallado un reto, para el reto y muestra el canvas de reto fallido
    /// </summary>
    private void ChallengeFailed()
    {
        _doingChallenge = false;
        HUDManager.Instance.ShowFailedChallengeCanvas();
        _challengeFailed = true;

    }




}
