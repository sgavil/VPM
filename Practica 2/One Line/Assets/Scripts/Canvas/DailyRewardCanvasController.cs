using UnityEngine;
using TMPro;


/// <summary>
/// Controla el canvas que aparece cuando se quiere recoger una recompensa diaria
/// </summary>
public class DailyRewardCanvasController : MonoBehaviour
{
    [Tooltip("Canvas que se muestra al pulsar el regalo gigante")]
    public GameObject rewardCanvas;

    [Tooltip("Canvas que se muestra al pulsar el boton de recoger recompensa")]
    public GameObject generalCanvas;

    [Tooltip("Texto que muestra el número de monedas recibidas")]
    public TextMeshProUGUI coinsRecievedText;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /// <summary>
    /// Muestra el canvas final donde se muestra la recompensa obtenida
    /// </summary>
    public void CollectReward()
    {
        generalCanvas.SetActive(false);
        rewardCanvas.SetActive(true);
    }

    /// <summary>
    /// Muestra un anuncio para duplicar las monedas
    /// </summary>
    public void ShowAd()
    {
        AdsManager.Instance.DuplicateCoinsAds();
    }

    /// <summary>
    /// Actualiza la cantidad de dinero actual con el dinero de la recompensa
    /// </summary>
    /// <param name="duplicated">Indica si se ha visto un anuncio para duplicar el dinero</param>
    public void AddDailyRewardMoney(bool duplicated)
    {
        if (duplicated)
            AdsManager.Instance.DuplicateCoinsAds();

        else ProgressManager.Instance.AddMoney(GameManager.Instance._dailyRewardMoney);

        HUDManager.Instance.UpdateMoneyText();
        HUDManager.Instance.ActivatePanel(false);
        rewardCanvas.SetActive(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    

    private void Start()
    {
        coinsRecievedText.text = "+" + GameManager.Instance._dailyRewardMoney.ToString();
    }
}
