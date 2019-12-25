using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundManager : MonoBehaviour
{
    public static SoundManager Instance;

    public AudioClip _back;
    public AudioClip _hint;
    public AudioClip _notEnoughMoney;
    public AudioClip _replay;
    public AudioClip _move;

    private AudioSource _audioSource;

    private void Awake()
    {
        Instance = this;
    }

    private void Start()
    {
        _audioSource = Camera.main.gameObject.GetComponent<AudioSource>();
    }

    public void PlayBack()
    {
        _audioSource.clip = _back;
        _audioSource.Play();
    }
    public void PlayHint()
    {
        _audioSource.clip = _hint;
        _audioSource.Play();
    }
    public void PlayNotEnoughMoney()
    {
        _audioSource.clip = _notEnoughMoney;
        _audioSource.Play();
    }

    public void PlayReplay()
    {
        _audioSource.clip = _replay;
        _audioSource.Play();
    }

    public void PlayMove()
    {
        _audioSource.clip = _move;
        _audioSource.Play();
    }

}
