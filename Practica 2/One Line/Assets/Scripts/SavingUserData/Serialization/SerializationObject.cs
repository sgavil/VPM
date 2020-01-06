using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.Text;
using System.IO;
using System.Security.Cryptography;

[Serializable]
public class SerializationObject
{
    public List<int> _levelsCompleted;

    public int _virtualCoin;
    public bool _adsBought;

    public int _challengeHour;
    public int _challengeMinutes;
    public int _challengeSeconds;

    public int _challengeDay;
    public int _challengeMonth;
    public int _challengeYear;

    public int _dailyRewardHour;
    public int _dailyRewardMinutes;
    public int _dailyRewardSeconds;

    public int _dailyRewardDay;
    public int _dailyRewardMonth;
    public int _dailyRewardYear;

    public string _serializationVersion;
    public int _completedChallenges;

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
    public DateTime getChallengeDate()
    {
        return new DateTime(_challengeYear, _challengeMonth, _challengeDay, _challengeHour, _challengeMinutes, _challengeSeconds);
    }
    public DateTime getDailyRewardDate()
    {
        return new DateTime(_dailyRewardYear, _dailyRewardMonth, _dailyRewardDay, _dailyRewardHour, _dailyRewardMinutes, _dailyRewardSeconds);
    }
}      

public class SerializationObjectChiper
{      
    public SerializationObject obj;
    public string _result = "";
}
