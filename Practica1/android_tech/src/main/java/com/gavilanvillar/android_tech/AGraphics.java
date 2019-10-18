package com.gavilanvillar.android_tech;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gavilanvillar.abs_layer.Graphics;
import com.gavilanvillar.abs_layer.Image;

import java.io.IOException;
import java.io.InputStream;

public class AGraphics implements Graphics {

    public AGraphics(AssetManager assetManager)
    {
        this._assetManager = assetManager;
    }

    @Override
    public Image newImage(String name) {
        Bitmap _sprite = null;

        InputStream _inputStream = null;
        try {

            _inputStream = _assetManager.open(name);
            _sprite = BitmapFactory.decodeStream(_inputStream);
        } catch (IOException e) {
            android.util.Log.e("AGraphics", "No se ha podido cargar el recurso: " + name);
        } finally {
            try {
                _inputStream.close();
            } catch (Exception io) {

            }
        }
        return new AImage(_sprite);
    }

    @Override
    public void clear(int color) {
        _canvas.drawColor(color);
    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public int getWidht() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    private AssetManager _assetManager;
    private Canvas _canvas;

}
