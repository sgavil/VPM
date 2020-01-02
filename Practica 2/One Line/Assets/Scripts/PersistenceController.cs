using System.IO;
using System.Security.Cryptography;
using System.Text;
using System;

public class PersistenceController
{
    private const string fileName = "prgs.json";
    private const string destination = fileName;

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

        string hashResult = ConvertObject(fileBytesToChiper);


        chiperObject.obj = obj;
        chiperObject._result = hashResult;

        File.AppendAllText(destination, ProgressManager.Instance.GetJson(chiperObject));
        file.Close();
    }

    public void LoadFile()
    {
        string destination = fileName;

        if (!File.Exists(destination))
            return;

        string readedJson = File.ReadAllText(destination);
        SerializationObjectChiper chiperObj = ProgressManager.Instance.GetChiperfromJson(readedJson);

        SerializationObject ReadedObj = chiperObj.obj;
       
        string testObjJson = ProgressManager.Instance.GetJson(ReadedObj);
        byte[] fileBytes = Encoding.UTF8.GetBytes(testObjJson);

        string testResult = ConvertObject(fileBytes);

        if (testResult == chiperObj._result)
        {
            ProgressManager.Instance.UpdateValues(ReadedObj);
        }


        else
        {
            //TODO:: hacer algo para que el jugador llore
        }
    }

    private string ConvertObject(byte[] fileBytes)
    {
        StringBuilder sb = new StringBuilder();

        using (SHA256Managed sha256 = new SHA256Managed())
        {
            byte[] hash = sha256.ComputeHash(fileBytes);
            foreach (Byte b in hash)
                sb.Append(b.ToString("X2"));
        }

        return sb.ToString();
    }
}
