using UnityEngine.UI;
using UnityEngine;
using System.Collections.Generic;
using TMPro;

using System.Collections;
using System;

public class HUDManager : MonoBehaviour
{
    public static HUDManager Instance;

    public Text _playerMoneyText;
    public Button movieButton;
    public Text notEnoughMoneyText;

    public List<TextMeshProUGUI> completedLevelsTexts;
    public TextMeshProUGUI completedChallengesTexts;

    public GameObject challengeCDPanel;
    public Text challengeCDText;
    public Button challengeButton;
    [Tooltip("Texto donde se muestra el precio de una pista.")]
    public Text _hintPriceText;

    [Header("Canvas del reto")]
    [Tooltip("Canvas que se muestra en el menu principal para poder jugar el reto")]
    public GameObject challengeCanvas;

    [Tooltip("Canvas que se muestra al completar exitosamente el reto")]
    public GameObject completedChallengeCanvas;

    [Tooltip("Canvas que se muestra al no completar el reto")]
    public GameObject failedChallengeCanvas;

    [Header("Elementos que NO aparecen al hacer el reto")]
    public GameObject bottomCanvasIcons;
    public GameObject moneyIcon;

    [Header("Elementos que SI aparecen al hacer el reto")]
    public TextMeshProUGUI challengeCountDownText;

    [Header("Recompensa diaria")]
    public GameObject dailyRewardCanvas;
    public Button _dailyRewardButton;

    public GameObject mainPanel;

    private const float notEnoughMoneyShowTime = 3.5f;      //Tiempo que dura la animacion del texto que indica que no hay dinero suficiente



    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;

        }
        else
            Destroy(gameObject);


    }
    private void Update()
    {
        if (challengeCDText != null)
        {
            challengeCDText.text = GameManager.Instance._challengeLeftTimeText;
        }
        if (challengeCountDownText != null)
        {
            float time = GameManager.Instance._currChallengeSeconds;
            string minutes = Mathf.Floor(time / 60).ToString("00");
            string seconds = (time % 60).ToString("00");
            challengeCountDownText.text = minutes + ":" + seconds;
        }
    }
    private void Start()
    {
        if (_hintPriceText != null)
            _hintPriceText.text = GameManager.Instance._hintPrice.ToString();
        if (_playerMoneyText != null)
            _playerMoneyText.text = ProgressManager.Instance._virtualCoin.ToString();

        if (challengeCDPanel != null && !GameManager.Instance._challengeAvailable)
        {
            challengeCDPanel.SetActive(true);
            challengeButton.interactable = false;
        }
        if (bottomCanvasIcons != null && GameManager.Instance._doingChallenge)
        {
            bottomCanvasIcons.SetActive(false);
            moneyIcon.SetActive(false);
            challengeCountDownText.gameObject.SetActive(true);
        }
        if (_dailyRewardButton != null)
        {
            _dailyRewardButton.interactable = GameManager.Instance._dailyRewardActive;
        }
        SetCompletedLevelsTexts();
    }
    public void MoveToLevel(int number)
    {
        GameManager.Instance.MoveToLevel(number);
    }
    public void MoveToLevelSelector(int n)
    {
        GameManager.Instance.LoadLevelSelector(n);
    }
    public void ClickBack()
    {
        GameManager.Instance.ClickBack();
    }
    public void UpdateMoneyText()
    {
        if (_playerMoneyText != null)
            _playerMoneyText.text = ProgressManager.Instance._virtualCoin.ToString();
    }
    private void SetCompletedLevelsTexts()
    {
        for (int i = 0; i < completedLevelsTexts.Count; i++)
        {
            string completedString = (ProgressManager.Instance.GetUnlockedLevelsOfCategory(i + 1)).ToString() + "/" + GameManager.Instance._levelsGroup._levels[i].Count.ToString();

            completedLevelsTexts[i].SetText(completedString);

        }
        if (completedChallengesTexts != null)
            SetCompletedChallengeNumber();
    }
    public void ShowRewardedAd()
    {
        AdsManager.Instance.showRewardedAd();
    }
    public void ClickHintButton()
    {
        GameManager.Instance.ClickHint();
    }

    public void ShowFailedChallengeCanvas()
    {
        if(failedChallengeCanvas!=null)
            failedChallengeCanvas.SetActive(true);
    }

    public IEnumerator ShowNotEnoughMoneyText()
    {
        if (notEnoughMoneyText.gameObject.activeSelf)
            yield return null;

        notEnoughMoneyText.gameObject.SetActive(true);
        yield return new WaitForSeconds(notEnoughMoneyShowTime);
        notEnoughMoneyText.gameObject.SetActive(false);
    }
    public void ShowChallengeCanvas()
    {

        challengeCanvas.SetActive(true);
        ActivatePanel(true);

    }
    private void SetCompletedChallengeNumber()
    {
        completedChallengesTexts.SetText(ProgressManager.Instance._completedChallenges.ToString());
    }
    public void DisableChallengePanel()
    {
        if (challengeCDPanel != null)
        {
            challengeCDPanel.SetActive(false);
            challengeButton.interactable = true;
        }
    }
    public void GoMainMenu()
    {
        GameManager.Instance.GoMainMenu();
    }
    public void ChallengeFailed()
    {
        GoMainMenu();
    }
    public void ShowDailyRewardCanvas()
    {
        dailyRewardCanvas.SetActive(true);
        ActivatePanel(true);
        _dailyRewardButton.interactable = false;
        GameManager.Instance._dailyRewardActive = false;
        ProgressManager.Instance.DailyRewardOpened();
    }
    public void ActivatePanel(bool active)
    {
        if(mainPanel!=null)
        mainPanel.SetActive(active);
    }
   
   
}
