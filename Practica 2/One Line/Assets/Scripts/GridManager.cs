using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GridManager : MonoBehaviour
{ 

    private int height = 0;
    private int width = 0;

    [SerializeField]
    private float tileSize = 1;


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

    public void GenerateGrid(GameObject referenceTile, List<string> layout)
    {
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

                    if (layout[y][x] == '1')
                        tile.GetComponent<Tile>().Pressed = false;
                    else if (layout[y][x] == '2')
                        tile.GetComponent<Tile>().Pressed = true;
                }

            }
        }

        SetGridAtInitialPosition();
    }

}
