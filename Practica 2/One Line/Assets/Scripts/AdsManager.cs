using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;

public class AdsManager : MonoBehaviour, IUnityAdsListener
{
    public static AdsManager Instance;

    private bool isTest = true;

    


#if UNITY_ANDROID 
    private string gameID = "3410958";
#endif

    void Awake()
    {
        if (Instance != null && Instance != this)
        {
            Destroy(this.gameObject);
        }

        Instance = this;

    }
    void Start()
    {

        Advertisement.AddListener(this);
        Advertisement.Initialize(gameID, true);
    }


    public void OnUnityAdsReady(string placementId)
    {
        // Optional actions to take when the end-users triggers an ad.
       // Advertisement.Show();
    }

    public void showRewardedAd()
    {

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

    public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
    {
        if (showResult == ShowResult.Finished)
        {
            // Reward the user for watching the ad to completion.
            addMoney();
            Debug.Log("anuncio acabado completo");
        }
        else if (showResult == ShowResult.Skipped)
        {
            // Do not reward the user for skipping the ad.
            Debug.Log("El usuario ha pasado el anuncio, no debe haber reward");

        }
        else if (showResult == ShowResult.Failed)
        {
            Debug.LogWarning("The ad did not finish due to an error.");
        }
    }

    private void addMoney()
    {
        GameManager.Instance.updateMoneyAdViewed(20);
    }
}
