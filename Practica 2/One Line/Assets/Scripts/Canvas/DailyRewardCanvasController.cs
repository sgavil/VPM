using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class DailyRewardCanvasController : MonoBehaviour
{
    public GameObject rewardCanvas;
    public GameObject generalCanvas;
    public TextMeshProUGUI coinsRecievedText;

    private void Start()
    {
        coinsRecievedText.text = "+"+GameManager.Instance._dailyRewardMoney.ToString();
    }
    public void CollectReward()
    {
        generalCanvas.SetActive(false);
        rewardCanvas.SetActive(true);
    }
    public void ShowAd()
    {
        AdsManager.Instance.DuplicateCoinsAds();
    }
    public void AddDailyRewardMoney(bool duplicated)
    {
        if (duplicated)
            ProgressManager.Instance.AddMoney(GameManager.Instance._dailyRewardMoney, 2);

        else ProgressManager.Instance.AddMoney(GameManager.Instance._dailyRewardMoney);
        HUDManager.Instance.UpdateMoneyText();
        HUDManager.Instance.ActivatePanel(false);
        rewardCanvas.SetActive(false);
    }
}
