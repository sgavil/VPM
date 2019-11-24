using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/BlockScriptableObject", order = 1)]
public class BlockScriptableObject: ScriptableObject
{
    public Sprite block;
    public Sprite hint;
    public Sprite touch;
}
