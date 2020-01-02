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

    public int _virtualCoin = 55;
    public bool _adsBought = false;
    public float _timeLeftToNextChallenge = 21.0f;
   

    public SerializationObject(List<int> levelsCompleted, int virtualCoin, bool adsBought, float timeLeftToNextChallenge)
    {
        _levelsCompleted = levelsCompleted;
        _virtualCoin = virtualCoin;
        _adsBought = adsBought;
        _timeLeftToNextChallenge = timeLeftToNextChallenge;
    }
    public SerializationObject()
    {
        _levelsCompleted = new List<int>();
        _virtualCoin = 0;
        _adsBought = false;
        _timeLeftToNextChallenge = 30.0f; //TODO

    }

  
}

public class SerializationObjectChiper
{
    public SerializationObject obj;
    public string _result = "";
}
