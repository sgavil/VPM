using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEditor;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    private const int levelSelectorSceneIndex = 2;

    [Tooltip("Archivos .json que almacenan los niveles.")]
    public List<TextAsset> _categoryLevelFiles;

    public LevelsGroup _levelsGroup;

    [Tooltip("Canvas generico de la aplicacion.")]
    public GameObject _genericCanvasGO;

    [Tooltip("Canvas superior de la aplicacion")]
    public GameObject _topCanvasGO;

    [Tooltip("Canvas inferior de la aplicacion")]
    public GameObject _bottomCanvasGO;

    [Tooltip("Texto donde se muestra el dinero actual del jugador.")]
    public Text _playerMoneyText;

    [Tooltip("Texto donde se muestra el precio de una pista.")]
    public Text _hintPriceText;

    [Tooltip("Precio de una pista.")]
    public int _hintPrice;

    [Tooltip("Porcentaje de la pantalla que pertenece a los bordes.")]
    public float _emptyLimitPercent;

    private Vector2 _screenSize;

    private Vector2 _genericCanvasSize;
    private Vector2 _bottomCanvasSize;
    private Vector2 _topCanvasSize;

    private Vector2 _emptyCanvasSize;
    private Vector3 _centerPositionEmptyCanvas;

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

    // Start is called before the first frame update
    void Awake()
    {
        if (Instance != null && Instance != this)
        {
            Destroy(this.gameObject);
        }

        Instance = this;

        _levelsGroup = new LevelsGroup();
        _levelsGroup.LoadLevelsFromJSON(_categoryLevelFiles);

        _screenSize = new Vector2(0, 0);
        _emptyLimitPercent /= 100.0f;
        Scaler();
        DontDestroyOnLoad(this);
    }

    private void Start()
    {
        if(_hintPriceText != null)
            _hintPriceText.text = _hintPrice.ToString();
        if(_playerMoneyText != null)
        _playerMoneyText.text = _playerMoney.ToString();


    }


    // Update is called once per frame
    void Update()
    {
        if (_screenSize.x != Screen.width || _screenSize.y != Screen.height)
            Scaler();

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

    public void LevelFinished()
    {
        Debug.Log("Finish");
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


    // CALCULO DEL ESPACIO CENTRAL SOBRANTE

    /// <summary>
    /// Realiza los calculos necesarios para guardar la informacion que se requiere para realizar
    /// el escalado de las entidades de la aplicacion.
    /// </summary>
    private void Scaler()
    {
        // Tamaño fisico de la pantalla
        _screenSize = new Vector2(Screen.width, Screen.height);

        // Tamaño del canvas generico
        _genericCanvasSize = new Vector2(_genericCanvasGO.GetComponent<RectTransform>().rect.width, 
            _genericCanvasGO.GetComponent<RectTransform>().rect.height);

        // Tamaño del canvas que se encuentra en la parte inferior de la pantalla
        _bottomCanvasSize = new Vector2((_bottomCanvasGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_bottomCanvasGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del canvas que se encuentra en la parte superior de la pantalla
        _topCanvasSize = new Vector2((_topCanvasGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_topCanvasGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del espacio sobrante
        float emptyWidth = _screenSize.x;
        float emptyHeight = _screenSize.y - (_topCanvasSize.y + _bottomCanvasSize.y);
        // Tamaño del espacio sobrante aplicando los limites
        _emptyCanvasSize = new Vector2(emptyWidth - (emptyWidth * (_emptyLimitPercent * 2)),
            (emptyHeight) - (emptyHeight * (_emptyLimitPercent * 2)));

        _centerPositionEmptyCanvas = new Vector3(_screenSize.x / 2,
            (_screenSize.y / 2) + (_topCanvasSize.y + (emptyHeight / 2) - (_screenSize.y / 2)),
            0.0f);


        GameManager.Instance.SetScreenSizeIsChanged(true);
    }

    /// <summary>
    /// Devuelve el espacio restante en pantalla que se encuentra entre el HUD superior y el inferior
    /// </summary>
    /// <returns>Espacio central restante</returns>
    public Vector2 GetEmptySpaceSize()
    {
        return _emptyCanvasSize;
    }

    public Vector3 GetEmptySpaceCenterPosition()
    {
        return _centerPositionEmptyCanvas;
    }
    public void LoadLevelSelector(int i)
    {
        _categoryLevel = i;
        Debug.Log("me he cambiado al selector de niveles " + i.ToString());
        SceneManager.LoadScene(levelSelectorSceneIndex);
    }
   
}
