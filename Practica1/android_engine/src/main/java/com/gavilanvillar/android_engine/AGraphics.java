package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;

import java.io.IOException;
import java.io.InputStream;

public class AGraphics implements Graphics {

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView) {
        this._assetManager = assetManager;
        this._surfaceView = surfaceView;
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

    public void lockCanvas(){
        while(!_surfaceView.getHolder().getSurface().isValid());
        _canvas = _surfaceView.getHolder().lockCanvas();
    }

    public void unlockCanvas(){
        _surfaceView.getHolder().unlockCanvasAndPost(_canvas);
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        if(image != null)
            _canvas.drawBitmap(((AImage)image).getImage(), new Rect(image.getHeight() * 4, 0, image.getHeight() * 5, image.getHeight()), new Rect(0, 0, getWidht(), getHeight()), null);

        //_canvas.drawBitmap(((AImage)image).getImage(), x, y, null);
    }

    @Override
    public int getWidht(){
        return _surfaceView.getWidth();
    }

    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
    }

    private AssetManager _assetManager;
    private SurfaceView _surfaceView;
    private Canvas _canvas;

}
