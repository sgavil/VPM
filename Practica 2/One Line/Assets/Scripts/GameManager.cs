using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEditor;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    [Tooltip("Archivos .json que almacenan los niveles.")]
    public List<TextAsset> _categoryLevelFiles;

    private LevelsGroup _levelsGroup;

    //---------------------------------------------------
    //TEMPORAL
    [Tooltip("Nombre de la categoría del nivel al que quieres acceder.")]
    [Range(1, 5)]
    public int _categoryLevel = 0;

    [Tooltip("Nivel del juego.")]
    public int _level = 0;
    //---------------------------------------------------

    private Vector2 _screenSize;

    public GameObject _genericCanvasGO;
    private Vector2 _genericCanvasSize;
    public GameObject _bottomCanvasGO;
    private Vector2 _bottomCanvasSize;
    public GameObject _topCanvasGO;
    private Vector2 _topCanvasSize;
    private Vector2 _emptyCanvasSize;
    private Vector3 _centerPositionEmptyCanvas;


    // Start is called before the first frame update
    void Awake()
    {
        Instance = this;

        _levelsGroup = new LevelsGroup();
        _levelsGroup.LoadLevelsFromJSON(_categoryLevelFiles);

        _screenSize = new Vector2(0, 0);
        Scaler();
    }

    private void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        if (_screenSize.x != Screen.width || _screenSize.y != Screen.height)
            Scaler();
    }
    

    /// <summary>
    /// Realiza los calculos necesarios para guardar la informacion que se requiere para realizar
    /// el escalado de las entidades de la aplicacion.
    /// </summary>
    private void Scaler()
    {
        // Tamaño fisico de la pantalla
        _screenSize = new Vector2(Screen.width, Screen.height);

        // Tamaño del canvas generico
        _genericCanvasSize = new Vector2(_genericCanvasGO.GetComponent<RectTransform>().rect.width, _genericCanvasGO.GetComponent<RectTransform>().rect.height);

        // Tamaño del canvas que se encuentra en la parte inferior de la pantalla
        _bottomCanvasSize = new Vector2((_bottomCanvasGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_bottomCanvasGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del canvas que se encuentra en la parte superior de la pantalla
        _topCanvasSize = new Vector2((_topCanvasGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_topCanvasGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del espacio sobrante
        _emptyCanvasSize = new Vector2(_screenSize.x, _screenSize.y - (_topCanvasSize.y + _bottomCanvasSize.y));
        _centerPositionEmptyCanvas = new Vector3(_screenSize.x / 2, 
            (_screenSize.y / 2) + (_topCanvasSize.y + (_emptyCanvasSize.y / 2) - (_screenSize.y / 2)),
            0.0f);
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

    /// <summary>
    /// Devuelve el nivel los datos del nivel elegido por el jugador
    /// </summary>
    /// <returns>Datos del nivel</returns>
    public LevelData GetLevel()
    {
        return _levelsGroup._levels[_categoryLevel - 1][_level - 1];
    }
}
