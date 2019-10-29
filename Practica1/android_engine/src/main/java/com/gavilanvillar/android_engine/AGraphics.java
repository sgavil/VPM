package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceView;
import android.view.Window;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Rect;

import java.io.IOException;
import java.io.InputStream;


/*
* El booleano de landscaped es para ver si esta tumbado o no, ya que cada vez que se tumba muere la activity y se vuelve a crear
*
* int frameBufferWidth =isLandscape ? 480 : 320;
 int frameBufferHeight =isLandscape ? 320 : 480;
*
* */
public class AGraphics extends AbstractGraphics {
    final int ANCHO_RES = 1080;
    final int ALTO_RES = 1920;

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView, boolean isLandscape, Point windowSize) {
        this._assetManager = assetManager;
        this._surfaceView = surfaceView;
        this._isLandscape = isLandscape;
        this._windowsHeight = windowSize.y;
        this._windowsWidth = windowSize.x;
    }

    @Override
    public void setCanvasSize(int x, int y){
        
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

        if(image != null) {
            _canvas.drawBitmap(((AImage)image).getImage(), x, y, null);
        }

    }

    @Override
    public int getWidth(){
        return _windowsWidth;
    }

    @Override
    public int getHeight() {
        return _windowsHeight;
    }

    private AssetManager _assetManager = null;
    private SurfaceView _surfaceView = null;
    private Canvas _canvas = null;

    private boolean _isLandscape;
    private int _windowsHeight;
    private int _windowsWidth;

}
