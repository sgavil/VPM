using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class ChallengeCanvasController : MonoBehaviour
{
    [Header("Textos canvas menu inicio")]
    public Text _challengeCostText;
    public Text _challengeMoneyObtainedText;
    public Text _challengeMedalsObtainedText;
    public Text _challengeSecondsText;
   

    [Header("Textos canvas reto completado")]
    public TextMeshProUGUI _obtainedCoinsText;
    public TextMeshProUGUI _obtainedMedalsText;

    

    private void Start()
    {
        if (!GameManager.Instance._doingChallenge)
        {
            if (_challengeCostText == null)
                return;

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
        HUDManager.Instance.ActivatePanel(false);
    }

    public void PlayChallenge()
    {
        if (GameManager.Instance.PlayChallenge(false))
            DisableCanvas();
        else StartCoroutine(HUDManager.Instance.ShowNotEnoughMoneyText());
    }
    public void WatchChallengeAd()
    {
        DisableCanvas();
        AdsManager.Instance.ChallengeAd();

    }
    public void ChallengeCompleted()
    {
        DisableCanvas();
        GameManager.Instance.ChallengeCompleted(false);
    }
    
    public void DuplicateCoins()
    {
        DisableCanvas();
        AdsManager.Instance.DuplicateCoinsAds();
        GameManager.Instance.ChallengeCompleted(true);


    }

}
