using UnityEngine;
using UnityEngine.Advertisements;
using System.Text;


/// <summary>
/// Genera los anuncios de la aplicación y controla si el usuario lo has pasado o los ha terminado correctamente.
/// </summary>
public class AdsManager : MonoBehaviour, IUnityAdsListener
{
    public static AdsManager Instance;

    private readonly bool isTest = true;

    //Controladores del tipo de anuncio
    private bool challengeAd = false;
    private bool duplicateCoins = false;
    private bool transitionAd = false;


#if UNITY_ANDROID
    private readonly string gameID = "3410958";
#elif UNITY_EDITOR
    private string gameID = "0";
#endif

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    /// <summary>
    /// Genera un anuncio para duplicar las monedas del jugador
    /// </summary>
    public void DuplicateCoinsAds()
    {
        duplicateCoins = true;
        ShowRewardedAd();
    }

    /// <summary>
    /// Genera un anuncio común para el usuario
    /// </summary>
    public void ShowRewardedAd()
    {
        Advertisement.Show();
    }

    /// <summary>
    /// Genera un auncio entre niveles
    /// </summary>
    public void ShowTransitionAd()
    {
        transitionAd = true;
        Advertisement.Show();
    }


    /// <summary>
    /// Genera un anuncio para poder jugar un reto gratis
    /// </summary>
    public void ChallengeAd()
    {
        challengeAd = true;
        ShowRewardedAd();
    }

    [HideInInspector]
    public string adsIDS = "ag78$44%%S";

    /// <summary>
    /// Método llamado cuando un anuncio finaliza, se comprueba si el usuario lo ha terminado o lo ha pasado para recompensarle o no.
    /// </summary>
    /// <param name="placementId"></param>
    /// <param name="showResult"></param>
    public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
    {
        if (showResult == ShowResult.Finished)
        {
            //Si es un anuncio para dupicar monedas y lo ha completado se le añade el doble de cantidad
            if (duplicateCoins)
            {
                ProgressManager.Instance.AddMoney(GameManager.Instance._challengeMoneyObtained, 2);
                duplicateCoins = false;
                HUDManager.Instance.UpdateMoneyText();
                return;
            }
            //Si ha completado correctamente el anuncio para jugar un reto gratis
            if (challengeAd)
            {
                GameManager.Instance.PlayChallenge(true);
                challengeAd = false;
                return;
            }
            //Auncio entre niveles
            if (transitionAd)
            {
                transitionAd = false;
                return;
            }
            AddMoney();
            HUDManager.Instance.UpdateMoneyText();
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

    }

    public void OnUnityAdsReady(string placementId)
    {

    }

    public void OnUnityAdsDidError(string message)
    {

    }

    public void OnUnityAdsDidStart(string placementId)
    {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    private void Awake()
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

        //Inicialización de UnityADS
        Advertisement.AddListener(this);
        Advertisement.Initialize(gameID, isTest);
        CheckID();
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

    /// <summary>
    /// Llama al manager de progreso para añadir dinero
    /// </summary>
    private void AddMoney()
    {
        ProgressManager.Instance.AddMoney(GameManager.Instance._adsMoneyObtained);
        
    }

}
