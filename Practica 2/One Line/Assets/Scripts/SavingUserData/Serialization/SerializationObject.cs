using System.Collections.Generic;
using System;
using UnityEngine;


[Serializable]
public class SerializationObject
{
    public List<int> _levelsCompleted;

    public int _virtualCoin;
    public bool _adsBought;

    //Fecha que guarda el último reto jugado
    public int _challengeHour;
    public int _challengeMinutes;
    public int _challengeSeconds;

    public int _challengeDay;
    public int _challengeMonth;
    public int _challengeYear;

    //Fecha que guarda la última recompensa diaria recogida

    public int _dailyRewardHour;
    public int _dailyRewardMinutes;
    public int _dailyRewardSeconds;

    public int _dailyRewardDay;
    public int _dailyRewardMonth;
    public int _dailyRewardYear;

    public string _serializationVersion;
    public int _completedChallenges;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////




    /// <summary>
    /// Crea un objeto de serializacion con los datos que se quieren mantener en el juego
    /// </summary>
    /// <param name="levelsCompleted">Número de niveles completados</param>
    /// <param name="virtualCoin">Moneda que posee el jugador</param>
    /// <param name="adsBought">Si ha comprado o no el bloqueador de anuncios</param>
    /// <param name="serializationVersion">Versión de serialización</param>
    /// <param name="completedChallenges">Número de retos completados</param>
    /// <param name="_challengeDate">Fecha en la que se ha jugado el último reto</param>
    /// <param name="_dailyRewardDate">Fecha en la que se ha recogido la última recompensa diaria</param>
    public SerializationObject(List<int> levelsCompleted, int virtualCoin, bool adsBought,
        string serializationVersion, int completedChallenges, DateTime _challengeDate, DateTime _dailyRewardDate)
    {
        _levelsCompleted = levelsCompleted;
        _virtualCoin = virtualCoin;
        _adsBought = adsBought;
        _serializationVersion = serializationVersion;
        _completedChallenges = completedChallenges;

        _challengeHour = _challengeDate.Hour;
        _challengeMinutes = _challengeDate.Minute;
        _challengeSeconds = _challengeDate.Second;

        _challengeDay = _challengeDate.Day;
        _challengeMonth = _challengeDate.Month;
        _challengeYear = _challengeDate.Year;


        _dailyRewardHour = _dailyRewardDate.Hour;
        _dailyRewardMinutes = _dailyRewardDate.Minute;
        _dailyRewardSeconds= _dailyRewardDate.Second;

        _dailyRewardDay = _dailyRewardDate.Day;
        _dailyRewardMonth= _dailyRewardDate.Month;
        _dailyRewardYear = _dailyRewardDate.Year;

    }
    public DateTime GetChallengeDate() => new DateTime(_challengeYear, _challengeMonth, _challengeDay, _challengeHour, _challengeMinutes, _challengeSeconds);

    public DateTime GetDailyRewardDate() => new DateTime(_dailyRewardYear, _dailyRewardMonth, _dailyRewardDay, _dailyRewardHour, _dailyRewardMinutes, _dailyRewardSeconds);

   
}      

/// <summary>
/// Clase que esta formada por un objeto de serializacion y el resultado de su cifrado
/// </summary>
public class SerializationObjectChiper
{      
    public SerializationObject obj;
    public string _result = "";     //Resultado del cifrado
}
