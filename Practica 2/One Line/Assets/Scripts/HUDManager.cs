using UnityEngine.UI;
using UnityEngine;
using System.Collections.Generic;
using TMPro;

using System.Collections;

public class HUDManager : MonoBehaviour
{
    public static HUDManager Instance;

    public Text _playerMoneyText;
    public Button movieButton;
    public Text notEnoughMoneyText;

    public List<TextMeshProUGUI> completedLevelsTexts;
    public TextMeshProUGUI completedChallengesTexts;

    [Header("Canvas del reto")]
    public GameObject challengeCanvas;
    public GameObject completedChallengeCanvas;

    public GameObject challengeCDPanel;
    public Text challengeCDText;



    private const float notEnoughMoneyShowTime = 3.5f;

    [Tooltip("Texto donde se muestra el precio de una pista.")]
    public Text _hintPriceText;

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
        if(challengeCDText != null)
        {
            challengeCDText.text = GameManager.Instance._challengeLeftTimeText;
        }
    }
    private void Start()
    {
        if (_hintPriceText != null)
            _hintPriceText.text = GameManager.Instance._hintPrice.ToString();
        if (_playerMoneyText != null)
            _playerMoneyText.text = ProgressManager.Instance._virtualCoin.ToString();

        if(challengeCDPanel != null && !GameManager.Instance._challengeAvailable)
        {
            challengeCDPanel.SetActive(true);
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
        if(_playerMoneyText!=null)
        _playerMoneyText.text = ProgressManager.Instance._virtualCoin.ToString();
    }
    private void SetCompletedLevelsTexts()
    {
        for (int i = 0; i < completedLevelsTexts.Count; i++)
        {
            string completedString = (ProgressManager.Instance.GetUnlockedLevelsOfCategory(i+1)).ToString()+"/"+ GameManager.Instance._levelsGroup._levels[i].Count.ToString();

            completedLevelsTexts[i].SetText(completedString);
            
        }
        if(completedChallengesTexts!= null)
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
    public IEnumerator ShowNotEnoughMoneyText()
    {
        notEnoughMoneyText.gameObject.SetActive(true);
        yield return new WaitForSeconds(notEnoughMoneyShowTime);
        notEnoughMoneyText.gameObject.SetActive(false);
    }
    public void ShowChallengeCanvas()
    {

        challengeCanvas.SetActive(true);
        
    }
    private void SetCompletedChallengeNumber()
    {
        
        completedChallengesTexts.SetText(ProgressManager.Instance._completedChallenges.ToString());
    }
    public void DisableChallengePanel()
    {
        if(challengeCDPanel != null)
            challengeCDPanel.SetActive(false);
    }
   
}
