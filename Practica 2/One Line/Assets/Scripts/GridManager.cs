using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GridManager : MonoBehaviour
{

    public ResourceManager _resourceManager;

    [SerializeField]
    private int height = 5;
    [SerializeField]
    private int width = 8;
    [SerializeField]
    private float tileSize = 1;

    private readonly int _defaultColor = 0;
    private int _actualColor = 0;

    // Start is called before the first frame update
    void Start()
    {
       
    }

    // Update is called once per frame
    void Update()
    {

    }

    public void SetGridSize(List<int> gridSize)
    {
        width = gridSize[0];
        height = gridSize[1];
    }

    private void SetGridAtInitialPosition()
    {
        float gridW = width * tileSize;
        float gridH = height * tileSize;
        transform.position = new Vector2(-gridW / 2 + tileSize / 2, gridH / 2 - tileSize / 2);
    }

    public void GenerateGrid(List<string> layout)
    {

        GameObject referenceTile = new GameObject("Block");
        SpriteRenderer renderer = referenceTile.AddComponent<SpriteRenderer>();
        renderer.sprite = _resourceManager._blockScriptableObjects[_defaultColor].block;

        for(int y = 0; y < layout.Count; y++)
        {
            for(int x = 0; x < layout[y].Length; x++)
            {
                if (layout[y][x] != '0')
                {
                    GameObject tile = (GameObject)Instantiate(referenceTile, transform);
                    float posX = x * tileSize;
                    float posY = y * -tileSize;

                    tile.transform.position = new Vector2(posX, posY);

                    if (layout[y][x] == '2')
                    {
                        tile.GetComponent<SpriteRenderer>().sprite = _resourceManager._blockScriptableObjects[_actualColor].block;
                    }
                }

            }
        }

        Destroy(referenceTile);

        SetGridAtInitialPosition();
    }

    public void SetActualColor(int c)
    {
        _actualColor = c;
    }

}
