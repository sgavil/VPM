using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;
using System.Text;
using System;

public class AdsManager : MonoBehaviour, IUnityAdsListener
{
    public static AdsManager Instance;

    private bool isTest = true;

    private bool challengeAd = false;
    private bool duplicateCoins = false;
    private bool transitionAd = false;


#if UNITY_ANDROID
    private string gameID = "3410958";
#elif UNITY_STANDALONE
    private string gameID = "0";
#endif

    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);

        }
        else
        {
            Destroy(gameObject);
            return;
        }

        Advertisement.AddListener(this);
        Advertisement.Initialize(gameID, true);
            
        Debug.Log("Ads manager inicializado correctamente");
        CheckID();
    }



    public void OnUnityAdsReady(string placementId)
    {
        // Optional actions to take when the end-users triggers an ad.
        // Advertisement.Show();
    }
    public void DuplicateCoinsAds()
    {
        duplicateCoins = true;
        showRewardedAd();
    }
    public void showRewardedAd()
    {

        Advertisement.Show();

    }
    public void showTransitionAd()
    {
        transitionAd = true;
        Advertisement.Show();
    }
    public void OnUnityAdsDidError(string message)
    {
        // Log the error.
    }

    public void OnUnityAdsDidStart(string placementId)
    {
        // Optional actions to take when the end-users triggers an ad.
    }
    [HideInInspector]
    public string adsIDS = "ag78$44%%S";

    public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
    {
        if (showResult == ShowResult.Finished)
        {
            if (duplicateCoins)
            {
                ProgressManager.Instance.AddMoney(GameManager.Instance._challengeMoneyObtained, 2);
                duplicateCoins = false;
                HUDManager.Instance.UpdateMoneyText();
                return;
            }
            if (challengeAd)
            {
                GameManager.Instance.PlayChallenge(true);
                challengeAd = false;
                return;
            }
            if (transitionAd)
            {
                transitionAd = false;
                return;
            }
            addMoney();



        }
        else if (showResult == ShowResult.Skipped)
        {

            if (challengeAd)
            {
                GameManager.Instance.AddSkippedWhileChallenge();
                challengeAd = false;

            }
            if (duplicateCoins)
            {
                ProgressManager.Instance.AddMoney(GameManager.Instance._challengeMoneyObtained);
                HUDManager.Instance.UpdateMoneyText();
                duplicateCoins = false;
                

            }

        }
        else if (showResult == ShowResult.Failed)
        {
            Debug.LogWarning("The ad did not finish due to an error.");
        }
    }

    public void ChallengeAd()
    {
        challengeAd = true;
       showRewardedAd();
    }

    private void CheckID()
    {
        StringBuilder sBuilder = new StringBuilder(GameManager.Instance.GameID.ToString().Length);
        foreach (char c in adsIDS)
        {
            sBuilder.Append(c ^ GameManager.Instance.GameID);
        }
        GameManager.Instance.gameID = sBuilder.ToString();
    }
    private void addMoney()
    {
        ProgressManager.Instance.UpdateMoneyAdViewed(GameManager.Instance._adsMoneyObtained);
    }

}
