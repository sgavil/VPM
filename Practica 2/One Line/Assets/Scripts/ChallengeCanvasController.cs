﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ChallengeCanvasController : MonoBehaviour
{
    [Header("Textos canvas menu inicio")]
    public Text _challengeCostText;
    public Text _challengeMoneyObtainedText;
    public Text _challengeMedalsObtainedText;
    public Text _challengeSecondsText;

    [Header("Textos canvas reto completado")]
    public Text _obtainedCoinsText;
    public Text _obtainedMedalsText;

    private void Start()
    {
        if (!GameManager.Instance._doingChallenge)
        {
            _challengeCostText.text = GameManager.Instance._challengeCost.ToString();
            _challengeMoneyObtainedText.text = "+" + GameManager.Instance._challengeMoneyObtained.ToString();
            _challengeMedalsObtainedText.text = "+" + GameManager.Instance._challengeMedalsObtained.ToString();
            _challengeSecondsText.text = GameManager.Instance._challengeSeconds.ToString() + "s";
        }
        else
        {
            _obtainedCoinsText.text = "+" + GameManager.Instance._challengeMoneyObtained.ToString(); ;
            _obtainedMedalsText.text = "+" + GameManager.Instance._challengeMedalsObtained.ToString();
        }
       
    }

    public void DisableCanvas()
    {
        gameObject.SetActive(false);
    }

    public void PlayChallenge()
    {
        if (GameManager.Instance.PlayChallenge(false))
            DisableCanvas();
    }
    public void WatchChallengeAd()
    {
        DisableCanvas();
        AdsManager.Instance.ChallengeAd();

    }
    public void ChallengeCompleted()
    {
        DisableCanvas();
        GameManager.Instance.ChallengeCompleted();
    }
    public void DuplicateCoins()
    {
        DisableCanvas();
        AdsManager.Instance.DuplicateCoinsAds();

    }
}
