using UnityEngine;
using UnityEngine.UI;
using TMPro;


/// <summary>
/// Controla el canvas que aparece cuando se pulsa el boton de realizar un reto y el que aparece cuando se completa
/// un reto
/// </summary>
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////



    /// <summary>
    /// Desactiva el canvas
    /// </summary>    
    public void DisableCanvas()
    {
        gameObject.SetActive(false);
        HUDManager.Instance.ActivatePanel(false);
    }

    /// <summary>
    /// Si se puede jugar el reto desactiva el canvas y se va a la pantalla del reto, si no,
    /// muestra el texto de que no hay dinero suficiente
    /// </summary>
    public void PlayChallenge()
    {
        if (GameManager.Instance.PlayChallenge(false))
            DisableCanvas();
        else StartCoroutine(HUDManager.Instance.ShowNotEnoughMoneyText());
    }

    /// <summary>
    /// Muestra un anuncio para poder jugar el reto y desactiva el canvas
    /// </summary>
    public void WatchChallengeAd()
    {
        DisableCanvas();
        AdsManager.Instance.ChallengeAd();

    }

    /// <summary>
    /// Avisa al game manager de que se ha completado el reto
    /// </summary>
    public void ChallengeCompleted()
    {
        DisableCanvas();
        GameManager.Instance.ChallengeCompleted(false);
    }
    /// <summary>
    /// Se llama cuando se decide duplicar el premio obtenido al completar el reto
    /// </summary>
    public void DuplicateCoins()
    {
        DisableCanvas();
        AdsManager.Instance.DuplicateCoinsAds();
        GameManager.Instance.ChallengeCompleted(true);


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

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


}
