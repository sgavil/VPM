using System.IO;
using System.Security.Cryptography;
using System.Text;
using System;
using UnityEngine;

/// <summary>
/// Guarda el progreso del juego y comprueba que el archivo no ha sido modificado de forma externa
/// </summary>
public class PersistenceController
{
    private const string fileName = "/prgs.json";                                       //Nombre del archivo de guardado
    private readonly string destination = Application.persistentDataPath + fileName;    //Ruta en la que se guarda el archivo
    public string ActualGameID = "784512";
    private byte[] FileBytes;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PÚBLICOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    /// <summary>
    /// Genera y guarda el archivo de progreso
    /// </summary>
    /// <param name="obj">Objeto que se utiliza para obtener los valores de los datos a guardar</param>
    public void SaveFile(SerializationObject obj)
    {
        FileStream file;

        if (File.Exists(destination))

            file = File.OpenWrite(destination);
        else
            file = File.Create(destination);

       
        file.Dispose();
        File.WriteAllText(destination, String.Empty);

        SerializationObjectChiper chiperObject = new SerializationObjectChiper();

        string jsonToChiper = ProgressManager.Instance.GetJson(obj);
        byte[] fileBytesToChiper =  Encoding.UTF8.GetBytes(jsonToChiper);
        ActualGameID = GameManager.Instance.gameID;
        FileBytes = Encoding.UTF8.GetBytes(ActualGameID);
        string hashResult = ConvertObject(fileBytesToChiper);

        chiperObject.obj = obj;
        chiperObject._result = hashResult;

        File.AppendAllText(destination, ProgressManager.Instance.GetJson(chiperObject));
        file.Close();
    }

    /// <summary>
    /// Carga el archivo del progreso del juego y actualiza los valores del manager de progreso
    /// </summary>
    public void LoadFile()
    {
        if (!File.Exists(destination))
            return;

        string readedJson = File.ReadAllText(destination);
        SerializationObjectChiper chiperObj = ProgressManager.Instance.GetChiperfromJson(readedJson);

        SerializationObject ReadedObj = chiperObj.obj;
       
        string testObjJson = ProgressManager.Instance.GetJson(ReadedObj);
        byte[] fileBytes = Encoding.UTF8.GetBytes(testObjJson);
        ActualGameID = GameManager.Instance.gameID;
        FileBytes = Encoding.UTF8.GetBytes(ActualGameID);

        string testResult = ConvertObject(fileBytes);

        if (testResult == chiperObj._result)
        {
            ProgressManager.Instance.UpdateValues(ReadedObj);
        }
        else
        {
            ProgressManager.Instance.ResetProgress(); 
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///                                                                                                 ///
    ///                                     MÉTODOS PRIVADOS                                            ///
    ///                                                                                                 ///
    ///////////////////////////////////////////////////////////////////////////////////////////////////////



    /// <summary>
    /// Dado un array de bytes genera una cadena de texto resultado del cifrado de dichos bytes
    /// </summary>
    /// <param name="fileBytes">Array de bytes que se van a cifrar</param>
    /// <returns>Resultado del cifrado</returns>
    private string ConvertObject(byte[] fileBytes)
    {
        StringBuilder sb = new StringBuilder();

        byte[] aux = new byte[FileBytes.Length + fileBytes.Length];
        aux = fileBytes;
        FileBytes.CopyTo(aux, FileBytes.Length);
        using (SHA256Managed sha256 = new SHA256Managed())
        {
            byte[] hash = sha256.ComputeHash(aux);
            foreach (Byte b in hash)
            {
                
                sb.Append(b.ToString("X2"));

            }
        }

        return sb.ToString();
    }
}
