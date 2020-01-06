using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("Sprite usado cuando el camino no está activo.")]
    public SpriteRenderer _defaultTile;

    [Tooltip("Sprite usado cuando el camino está activo.")]
    public SpriteRenderer _colourTile;

    private BoardManager _boardManager;
    private Animator _animator;
    

    public void SetPressed(bool pressed)
    {
        _defaultTile.enabled = !pressed;
        _colourTile.enabled = pressed;
    }
    private void Start()
    {
        _boardManager = transform.parent.parent.GetComponent<BoardManager>();
        _animator = GetComponent<Animator>();
    }
    private void Update()
    {
        if (_boardManager.doingScalingDown)
        {
            _animator.SetBool("ScaleDown", true);
           
        }
        else _animator.SetBool("ScaleDown", false);

        if (_boardManager.doingScalingUp)
        {
            _animator.SetBool("ScaleUp", true);
        }
        else _animator.SetBool("ScaleUp", false);
    }


}
