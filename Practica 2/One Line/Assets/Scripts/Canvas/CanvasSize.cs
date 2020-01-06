using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CanvasSize : MonoBehaviour
{

    [Tooltip("Panel superior de la aplicacion")]
    public GameObject _topPanelGO;

    [Tooltip("Panel inferior de la aplicacion")]
    public GameObject _bottomPanelGO;

    [Tooltip("Espacio entre los dos paneles superior e inferior")]
    public RectTransform _middlePanelRect;

    private RectTransform _topPanelRect;
    private RectTransform _botPanelRect;


    [Tooltip("Porcentaje de la pantalla que pertenece a los bordes.")]
    public float _emptyDiffHeight;
    public float _emptyDiffWidth;

    private Vector2 _screenSize;

    private Vector2 _genericCanvasSize;
    private Vector2 _bottomCanvasSize;
    private Vector2 _topCanvasSize;

    private Vector2 _emptyCanvasSize;
    private Vector3 _centerPositionEmptyCanvas;

    // Start is called before the first frame update
    void Start()
    {
        _screenSize = new Vector2(0, 0);
        _topPanelRect = _topPanelGO.GetComponent<RectTransform>();
        _botPanelRect = _bottomPanelGO.GetComponent<RectTransform>();

        if(_middlePanelRect!=null)
            calculateMiddleSpace();
        Scaler();
    }

    // Update is called once per frame
    void Update()
    {
        if (_screenSize.x != Screen.width || _screenSize.y != Screen.height)
        {
            Scaler();
            GameManager.Instance.SetScreenSizeIsChanged(true);
        }
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
        _genericCanvasSize = new Vector2(GetComponent<RectTransform>().rect.width,
            GetComponent<RectTransform>().rect.height);

        // Tamaño del canvas que se encuentra en la parte superior de la pantalla
        _topCanvasSize = new Vector2((_topPanelGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_topPanelGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del canvas que se encuentra en la parte inferior de la pantalla
        _bottomCanvasSize = new Vector2((_bottomPanelGO.GetComponent<RectTransform>().rect.width * _screenSize.x) / _genericCanvasSize.x,
            (_bottomPanelGO.GetComponent<RectTransform>().rect.height * _screenSize.y) / _genericCanvasSize.y);

        // Tamaño del espacio sobrante
        float emptyWidth = _screenSize.x;
        float emptyHeight = _screenSize.y - (_topCanvasSize.y + _bottomCanvasSize.y);

        // Tamaño del espacio sobrante aplicando los limites
        _emptyCanvasSize = new Vector2(emptyWidth - ((_emptyDiffWidth * Screen.width) / 1080),
            emptyHeight - ((_emptyDiffHeight * Screen.height) / 2160));


        _centerPositionEmptyCanvas = new Vector3(_screenSize.x / 2,
            (_screenSize.y / 2) + (_topCanvasSize.y + (emptyHeight / 2) - (_screenSize.y / 2)),
            0.0f);
       // _middlePanelRect.sizeDelta = _emptyCanvasSize;
        //_middlePanelRect.position = _centerPositionEmptyCanvas;

        Debug.Log("empty: " + emptyHeight.ToString());
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
    private void calculateMiddleSpace()
    {
        float TopPanelWidth = _topPanelRect.rect.width * _topPanelRect.localScale.x;
        float TopPanelHeight = _topPanelRect.rect.height * _topPanelRect.localScale.y;

        float BotPanelWidth = _botPanelRect.rect.width * _botPanelRect.localScale.x;
        float BotPanelHeight = _botPanelRect.rect.height * _botPanelRect.localScale.y;

        float destWidth = _screenSize.x;
        float destHeight = _screenSize.y - TopPanelHeight - BotPanelHeight;

        //_middlePanelRect.rect.Set(0, _topPanelRect.rect.y + TopPanelHeight, TopPanelWidth, destHeight);
        // _middlePanelRect.position = new Vector3(0, _topPanelRect.rect.y + TopPanelHeight,0);
        _middlePanelRect.sizeDelta = new Vector2(TopPanelWidth, _topPanelRect.rect.y + TopPanelHeight);

    }
}
