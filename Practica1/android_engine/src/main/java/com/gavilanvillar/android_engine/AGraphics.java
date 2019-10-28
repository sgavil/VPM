package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;

import java.io.IOException;
import java.io.InputStream;

public class AGraphics implements Graphics {
    final int ANCHO_RES = 1080;
    final int ALTO_RES = 1920;

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
        setScaleFactor();
        if(image != null) {
            Bitmap resize = Bitmap.createScaledBitmap(((AImage) image).getImage(), (int) (image.getWidth() * _scaleFactorX),
                    (int) (image.getHeight() * _scaleFactorY), false);
            _canvas.drawBitmap(resize, 0, 0, null);
        }

        //_canvas.drawBitmap(((AImage)image).getImage(), x, y, null);
    }

    public void setScaleFactor(){
        _scaleFactorX = ((float)getWidth() / (float)ANCHO_RES);
        _scaleFactorY = ((float)getHeight() / (float)ALTO_RES);
    }

    @Override
    public int getWidth(){
        return _surfaceView.getWidth();
    }

    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
    }

    private AssetManager _assetManager = null;
    private SurfaceView _surfaceView = null;
    private Canvas _canvas = null;

    private float _scaleFactorX = 0;
    private float _scaleFactorY = 0;

}
