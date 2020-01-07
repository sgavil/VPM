using UnityEngine.UI;
using UnityEngine;
using System.Collections.Generic;
using TMPro;
using System.Collections;


/// <summary>
/// Manager que permite el control de las pulsaciones los botones y la aparicion y desaparicion de canvas
/// </summary>
public class HUDManager : MonoBehaviour
{
    public static HUDManager Instance;

    [Header("Elementos correspondientes al nivel")]
    public Text _playerMoneyText;
    public Button movieButton;
    public Text notEnoughMoneyText;
    [Tooltip("Texto donde se muestra el precio de una pista.")]
    public Text _hintPriceText;

    [Header("Textos que muestran el progreso del menú principal")]
    public List<TextMeshProUGUI> completedLevelsTexts;
    public TextMeshProUGUI completedChallengesTexts;

    [Header("Elementos del reto")]
    public GameObject challengeCDPanel;
    public Text challengeCDText;
    public Button challengeButton;


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

    [Space]
    [Tooltip("Panel principal que se utiliza para oscurecer el fondo")]
    public GameObject mainPanel;

    private const float notEnoughMoneyShowTime = 3.5f;      //Tiempo que dura la animacion del texto que indica que no hay dinero suficiente

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////




    /// <summary>
    /// Carga un nivel
    /// </summary>
    /// <param name="number">Número del nivel que se carga</param>
    public void MoveToLevel(int number)
    {
        GameManager.Instance.MoveToLevel(number);
    }

    /// <summary>
    /// Pulsacion de boton para moverse a un selector de nivel
    /// </summary>
    /// <param name="n">Categoría a la que se mueve</param>
    public void MoveToLevelSelector(int n)
    {
        GameManager.Instance.LoadLevelSelector(n);
    }

    /// <summary>
    /// Permite a los botones volver atrás
    /// </summary>
    public void ClickBack()
    {
        GameManager.Instance.ClickBack();
    }

    /// <summary>
    /// Actualiza el texto del dinero
    /// </summary>
    public void UpdateMoneyText()
    {
        if (_playerMoneyText != null)
            _playerMoneyText.text = ProgressManager.Instance._virtualCoin.ToString();
    }

    /// <summary>
    /// Muestra un anuncio
    /// </summary>
    public void ShowRewardedAd()
    {
        AdsManager.Instance.ShowRewardedAd();
    }

    /// <summary>
    /// Llamado cuando se pulsa el boton de pedir una pista
    /// </summary>
    public void ClickHintButton()
    {
        GameManager.Instance.ClickHint();
    }

    /// <summary>
    /// Muestra el canvas de reto no conseguido
    /// </summary>
    public void ShowFailedChallengeCanvas()
    {
        if (failedChallengeCanvas != null)
            failedChallengeCanvas.SetActive(true);
    }

    /// <summary>
    /// Muestra la animacion cuando no se tiene dinero para comprar algo
    /// </summary>
    /// <returns></returns>
    public IEnumerator ShowNotEnoughMoneyText()
    {
        if (notEnoughMoneyText.gameObject.activeSelf)
            yield return null;

        notEnoughMoneyText.gameObject.SetActive(true);
        yield return new WaitForSeconds(notEnoughMoneyShowTime);
        notEnoughMoneyText.gameObject.SetActive(false);
    }

    /// <summary>
    /// Muestra el canvas del reto
    /// </summary>
    public void ShowChallengeCanvas()
    {

        challengeCanvas.SetActive(true);
        ActivatePanel(true);

    }

    /// <summary>
    /// Desactiva el panel del reto
    /// </summary>
    public void DisableChallengePanel()
    {
        if (challengeCDPanel != null)
        {
            challengeCDPanel.SetActive(false);
            challengeButton.interactable = true;
        }
    }

    /// <summary>
    /// Metodo llamado para volver al menu principal
    /// </summary>
    public void GoMainMenu()
    {
        GameManager.Instance.GoMainMenu();
    }

    /// <summary>
    /// Metodo llamado cuando el reto se falla
    /// </summary>
    public void ChallengeFailed()
    {
        GoMainMenu();
    }

    /// <summary>
    /// Muestra el canvas de la recompensa diaria
    /// </summary>
    public void ShowDailyRewardCanvas()
    {
        dailyRewardCanvas.SetActive(true);
        ActivatePanel(true);
        _dailyRewardButton.interactable = false;
        GameManager.Instance._dailyRewardActive = false;
        ProgressManager.Instance.DailyRewardOpened();
    }

    /// <summary>
    /// Permite controlar la activacion y desactivacion del panel principal
    /// </summary>
    /// <param name="active">Valor al que se quiere poner el panel</param>
    public void ActivatePanel(bool active)
    {
        if (mainPanel != null)
            mainPanel.SetActive(active);
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

    /// <summary>
    /// Actualiza el valor del texto de los retos completados
    /// </summary>
    private void SetCompletedChallengeNumber()
    {
        completedChallengesTexts.SetText(ProgressManager.Instance._completedChallenges.ToString());
    }

    /// <summary>
    /// Da valor a los textos del menu principal de los niveles completados de cada categoria
    /// </summary>
    private void SetCompletedLevelsTexts()
    {
        for (int i = 0; i < completedLevelsTexts.Count; i++)
        {
            string completedString = (ProgressManager.Instance.GetUnlockedLevelsOfCategory(i + 1)).ToString() + "/" + GameManager.Instance._levelsGroup.GetNumberOfLevels(i + 1).ToString();

            completedLevelsTexts[i].SetText(completedString);

        }
        if (completedChallengesTexts != null)
            SetCompletedChallengeNumber();
    }

}
