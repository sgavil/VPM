using System.IO;
using System.Security.Cryptography;
using System.Text;
using System;
using UnityEngine;

public class PersistenceController
{
    private const string fileName = "/prgs.json";
    private string destination = Application.persistentDataPath + fileName;
    public string ActualGameID = "784512";
    private byte[] FileBytes;

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
