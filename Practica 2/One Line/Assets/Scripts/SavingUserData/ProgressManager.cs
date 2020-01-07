using System.Collections.Generic;
using UnityEngine;
using System;

/// <summary>
/// Controla el progreso del juego y sirve como comunicación entre la serialización y la persistencia de los datos del juego.
/// </summary>
public class ProgressManager : MonoBehaviour
{
    
    public int _virtualCoin = 0;                    //Número de monedas
    public bool _adsBought = false;                 //Si ha comprado o no el desbloqueo de anuncios
    public string _serializationVersion = "0.01";   //Version de serialización
    public int _completedChallenges = 0;            //Número de retos completados

    //Fechas del ultimo regalo y reto 
    public DateTime _timeWhenChallengeDone;
    public DateTime _timeWhenDailyRewardOpened;

    public static ProgressManager Instance;

    private PersistenceController persistenceController;
    private List<int> levelsCompleted = new List<int>();

    [HideInInspector]
    public string progressID;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////



    /// <summary>
    /// Resetea el progreso del jugador
    /// </summary>
    public void ResetProgress()
    {
        for (int i = 0; i < levelsCompleted.Count; i++)
        {
            levelsCompleted[i] = 0;
        }
        _virtualCoin = 0;
        _adsBought = false;

        _completedChallenges = 0;

    }
    /// <summary>
    /// Actualiza la fecha de la última recompensa diaria recogida a el momento en el que es llamado 
    /// </summary>
    public void DailyRewardOpened()
    {
        _timeWhenDailyRewardOpened = DateTime.Now;
    }

    /// <summary>
    /// Agrega dinero al dinero actual
    /// </summary>
    /// <param name="n">Cantidad a agregar</param>
    public void AddMoney(int n)
    {
        _virtualCoin += n;
    }

    /// <summary>
    /// Agrega dinero al dinero actual con un factor de multiplicación
    /// </summary>
    /// <param name="n">Cantidad de dinero a agregar</param>
    /// <param name="multiplicationFactor">Factor por el que se multiplica la cantidad a agregar</param>
    public void AddMoney(int n, int multiplicationFactor)
    {
        _virtualCoin += n * multiplicationFactor;
    }

    /// <summary>
    /// Añade un nivel a la lista de niveles completados en una categoría específica
    /// </summary>
    /// <param name="category">Categoría a la que pertenece el nivel</param>
    public void LevelCompleted(int category)
    {
        levelsCompleted[category] += 1;
    }

    /// <summary>
    /// Obtiene el json de un Objeto de serializacion
    /// </summary>
    /// <param name="obj">Objeto de serializacion</param>
    /// <returns>Json del objeto</returns>
    public string GetJson(SerializationObject obj)
    {
        return JsonUtility.ToJson(obj);

    }
    /// <summary>
    /// Obtiene el json de un Objeto de serializacion cifrado
    /// </summary>
    /// <param name="obj">Objeto de serializacion cifrado</param>
    /// <returns>Json del objeto</returns>
    public string GetJson(SerializationObjectChiper obj)
    {
        return JsonUtility.ToJson(obj);

    }
   

    /// <summary>
    /// Devuelve el valor de los niveles completados de una categoría específica
    /// </summary>
    /// <param name="nCategory">Categoría por la cual se pregunta</param>
    /// <returns>Número de niveles desbloqueados</returns>
    public int GetUnlockedLevelsOfCategory(int nCategory)
    {
        return levelsCompleted[nCategory - 1] + 1;
    }

    /// <summary>
    /// Actualiza los valores actuales a los que se obtienen por parámetro
    /// </summary>
    /// <param name="obj">Contenedor de los valores más actuales</param>
    public void UpdateValues(SerializationObject obj)
    {
        levelsCompleted = obj._levelsCompleted;
        _virtualCoin = obj._virtualCoin;
        _adsBought = obj._adsBought;
        _timeWhenChallengeDone = obj.GetChallengeDate();
        _serializationVersion = obj._serializationVersion;
        _timeWhenDailyRewardOpened = obj.GetDailyRewardDate();
        _completedChallenges = obj._completedChallenges;

    }
    /// <summary>
    /// Crea un objeto de serializacion con los valores actuales y guarda el progreso
    /// </summary>
    public void SaveProgress()
    {
        SerializationObject sObj = new SerializationObject(levelsCompleted, _virtualCoin, _adsBought,
             _serializationVersion, _completedChallenges, _timeWhenChallengeDone,_timeWhenDailyRewardOpened);

        persistenceController.SaveFile(sObj);
    }
    /// <summary>
    /// Crea un objeto de serialización cifrado a traves de un json y lo devuelve
    /// </summary>
    /// <param name="json">Json a serializar</param>
    /// <returns>Devuelve el objeto cifrado</returns>
    public SerializationObjectChiper GetChiperfromJson(string json)
    {
        return JsonUtility.FromJson<SerializationObjectChiper>(json);
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

        for (int i = 0; i < GameManager.Instance._categoryLevelFiles.Count; i++)
        {
            levelsCompleted.Add(0);
        }

        persistenceController = new PersistenceController();
        persistenceController.LoadFile();
    }

    /// <summary>
    /// Guarda el progreso cuando se cierra la aplicacion
    /// </summary>
    private void OnApplicationQuit()
    {
        SaveProgress();
    }

    /// <summary>
    /// Guarda el progreso cuando se pierde el foco de la aplicación
    /// </summary>
    private void OnApplicationFocus(bool focus)
    {
        if (!focus)
            SaveProgress();
    }

   
}
