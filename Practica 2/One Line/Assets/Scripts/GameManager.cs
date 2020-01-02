using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEditor;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    private const int mainMenuScene = 0;
    private const int levelSelectorSceneIndex = 1;
    private const int levelScene = 2;

    [Tooltip("Archivos .json que almacenan los niveles.")]
    public List<TextAsset> _categoryLevelFiles;

    public LevelsGroup _levelsGroup;

    [Tooltip("Texto donde se muestra el dinero actual del jugador.")]
    public Text _playerMoneyText;

    [Tooltip("Texto donde se muestra el precio de una pista.")]
    public Text _hintPriceText;

    [Tooltip("Precio de una pista.")]
    public int _hintPrice;



    public int _playerMoney
    {
        get {
            return _money;
        }
        set
        {
            _money = value;
        }
    }
    private int _money;
    //---------------------------------------------------
    //TEMPORAL
    [Tooltip("Nombre de la categoría del nivel al que quieres acceder.")]
    [Range(1, 5)]
    public int _categoryLevel = 0;

    [Tooltip("Nivel del juego.")]
    public int _level = 0;

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
            Destroy(gameObject);

        _levelsGroup = new LevelsGroup();
        _levelsGroup.LoadLevelsFromJSON(_categoryLevelFiles);

        DontDestroyOnLoad(this);
    }

    private void Start()
    {
        if(_hintPriceText != null)
            _hintPriceText.text = _hintPrice.ToString();
        if(_playerMoneyText != null)
        _playerMoneyText.text = _playerMoney.ToString();


    }

    public void updateMoneyAdViewed(int n)
    {
        _money += n;
        _playerMoneyText.text = _money.ToString();
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

    //public void LevelFinished()
    //{
    //    Debug.Log("Finish");
    //}

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

    // BOTONES

    public void ClickBack()
    {
        SoundManager.Instance.PlayBack();
    }
    public void ClickHint()
    {
        if (_playerMoney >= _hintPrice)
        {
            FindObjectOfType<BoardManager>().UserWantHint();
            _playerMoney -= _hintPrice;
            _playerMoneyText.text = _playerMoney.ToString();
        }
        else
        {
            SoundManager.Instance.PlayNotEnoughMoney();
            Debug.Log("No tienes dinero suficiente");
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
        SceneManager.LoadScene(levelSelectorSceneIndex);
    }

    public void GoMainMenu()
    {
        SceneManager.LoadScene(mainMenuScene);
    }
   
    public void MoveToLevel(int number)
    {
        Debug.Log("me he movido al numero: " + number.ToString());
        _level = number;
        SceneManager.LoadScene(levelScene);
    }
}
