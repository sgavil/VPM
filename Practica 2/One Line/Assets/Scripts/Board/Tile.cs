using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el camino no está activo.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando el camino está activo.")]
    public SpriteRenderer _colourTile;

    private BoardManager _boardManager;     //Referencia al boardManager
    private Animator _animator;             //Animator del tile para la animación de escalado


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    /// <summary>
    /// Cambia el estado de activación del tile
    /// </summary>
    /// <param name="pressed">Controla si cambia a activo o inactivo</param>
    public void SetPressed(bool pressed)
    {
        _defaultTile.enabled = !pressed;
        _colourTile.enabled = pressed;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private void Start()
    {
        _boardManager = transform.parent.parent.GetComponent<BoardManager>();
        _animator = GetComponent<Animator>();
    }
    private void Update()
    {
        SetCurrentAnimationState();

    }

    /// <summary>
    /// Método que actualiza el estado de la animación del tile entre desaparición y aparición
    /// </summary>
    private void SetCurrentAnimationState()
    {
        if (_boardManager.doingScalingDown)
        {
            _animator.SetBool("ScaleUp", false);
            _animator.SetBool("ScaleDown", true);
        }

        else if (_boardManager.doingScalingUp)
        {
            _animator.SetBool("ScaleDown", false);
            _animator.SetBool("ScaleUp", true);
        }
    }
}
