using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CanvasManager : MonoBehaviour
{

    private Vector2 _screenSize;

    private Vector2 _genericCanvasSize;
    public GameObject _bottomCanvasGO;
    private Vector2 _bottomCanvasSize;
    public GameObject _topCanvasGO;
    private Vector2 _topCanvasSize;
    private Vector2 _emptyCanvasSize;
    private Vector3 _centerPositionEmptyCanvas;

    // Start is called before the first frame update
    void Start()
    {
        _screenSize = new Vector2(0, 0);
        Scaler();
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
        _genericCanvasSize = new Vector2(GetComponent<RectTransform>().rect.width, GetComponent<RectTransform>().rect.height);

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
}
