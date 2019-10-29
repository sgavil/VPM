package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.Window;

import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;

import java.io.IOException;
import java.io.InputStream;


/*
* El booleano de landscaped es para ver si esta tumbado o no, ya que cada vez que se tumba muere la activity y se vuelve a crear
*
* int frameBufferWidth =isLandscape ? 480 : 320;
 int frameBufferHeight =isLandscape ? 320 : 480;
*
* */
public class AGraphics implements Graphics {
    final int ANCHO_RES = 1080;
    final int ALTO_RES = 1920;

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView, boolean isLandscape, Point windowSize) {
        this._assetManager = assetManager;
        this._surfaceView = surfaceView;
        this._isLandscape = isLandscape;
        this._windowsHeight = windowSize.y;
        this._windowsWidth = windowSize.x;

        setScaleFactor();
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
            Bitmap resize = Bitmap.createScaledBitmap(((AImage) image).getImage(), (int) (image.getWidth() * _scaleFactorX),
                    (int) (image.getHeight() * _scaleFactorY), false);
            _canvas.drawBitmap(resize, x, y, null);
        }
    }

    @Override
    public void drawImage(Image image, int srcX,int srcY,int destY,int destX,int cellsX,int cellsY){
        if(image != null) {
            int cellWidth = (((AImage) image).getImage()).getWidth() / cellsX;
            int cellHeight = (((AImage) image).getImage()).getHeight() / cellsY;

            Bitmap cuttedMap = Bitmap.createBitmap((((AImage) image).getImage()),cellWidth*srcX,cellHeight*srcY,cellWidth,cellHeight);

            Bitmap resize = Bitmap.createScaledBitmap(cuttedMap, (int) (cuttedMap.getWidth() * _scaleFactorX),
                    (int) (cuttedMap.getHeight() * _scaleFactorY), false);

            _canvas.drawBitmap(resize, destX,destY, null);
        }
    }


    public void setScaleFactor(){

        int width = _isLandscape ? _windowsHeight : _windowsWidth;
        int height = _isLandscape ? _windowsWidth : _windowsHeight;

        _scaleFactorX = ((float)width/ (float)ANCHO_RES);
        _scaleFactorY = ((float)height / (float)ALTO_RES);
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

    private float _scaleFactorX = 0;
    private float _scaleFactorY = 0;

    private boolean _isLandscape;
    private int _windowsHeight;
    private int _windowsWidth;

}
