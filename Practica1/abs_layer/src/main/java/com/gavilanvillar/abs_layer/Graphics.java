package com.gavilanvillar.abs_layer;

public interface Graphics {
    Image newImage(String name);

    void clear(int color);

    void drawImage(Image image);

    int getWidht();

    int getHeight();
}
