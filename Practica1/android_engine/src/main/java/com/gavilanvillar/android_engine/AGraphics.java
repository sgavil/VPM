package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;
import android.view.Window;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Graphics;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Rect;
import com.gavilanvillar.engine.Sprite;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase AGraphics
 *
 * Heredada de la clase AbstractGraphics
 */
public class AGraphics extends AbstractGraphics {

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView, Point windowSize) {
        this._assetManager = assetManager;
        this._surfaceView = surfaceView;
        super.setPhysicResolution(windowSize.x, windowSize.y);
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
    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha) {
        if(image != null) {
            android.graphics.Rect src = new android.graphics.Rect(srcRect._left, srcRect._top,
                    srcRect._right, srcRect._bottom);
            android.graphics.Rect dest = new android.graphics.Rect(destRect._left, destRect._top,
                    destRect._right, destRect._bottom);

            Paint alphaPaint = new Paint();
            alphaPaint.setAlpha((int)(alpha * 255));
            _canvas.drawBitmap(((AImage)image).getImage(), src, dest, alphaPaint);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private AssetManager _assetManager = null;
    private SurfaceView _surfaceView = null;
    private Canvas _canvas = null;
}
