using UnityEngine;

/// <summary>
/// Scriptable Object que sirve como "pieles" para los bloques
/// </summary>
[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/BlockScriptableObject", order = 1)]
public class BlockScriptableObject: ScriptableObject
{
    public Sprite block;        //Color del bloque por defecto
    public Sprite hint;         //Color de los puntos de ayuda
    public Sprite touch;        //Color del circulo que aparece al realizar una pulsación
}
