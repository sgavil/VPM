package com.gavilanvillar.android_engine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

import com.gavilanvillar.engine.AbstractGraphics;
import com.gavilanvillar.engine.Image;
import com.gavilanvillar.engine.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase AGraphics
 *
 * Hereda de AbstractGraphics
 */
public class AGraphics extends AbstractGraphics {

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //    Constructora y métodos de inicialización (de AGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView, Point windowSize) {
        this._assetManager = assetManager;
        this._surfaceView = surfaceView;

        // Guarda el tamaño de físico de la pantalla
        setPhysicResolution(windowSize.x, windowSize.y);
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //           Métodos reimplementados (de Graphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Crea un nuevo Bitmap y lo almacena en la clase AImage
    @Override
    public Image newImage(String name) {

        Bitmap image = null;
        InputStream _inputStream = null;

        try {

            // Abre la lectura del archivo
            _inputStream = _assetManager.open(name);

            // Codifica ese archivo a un Bitmap
            image = BitmapFactory.decodeStream(_inputStream);

        } catch (IOException e) {

            android.util.Log.e("AGraphics", "No se ha podido cargar el recurso: " + name);

        } finally {

            try {

                // Cierra la lectura del archivo
                _inputStream.close();

            } catch (Exception io) {
                android.util.Log.e("AGraphics", "No se ha podido cerrar InputStream");
            }

        }

        // Devuelve una nueva AImage
        return new AImage(image);

    } // newImage

    @Override
    public void clear(int color) {

        // Pinta el canvas con un color determinado
        _canvas.drawColor(color);

    } // clear

    @Override
    public void drawImagePrivate(Image image, Rect srcRect, Rect destRect, float alpha) {

        // Se comprueba que existe el objeto "image" para evitar fallos
        if(image != null) {

             // Crea un rectángulo fuente de Java con la información que obtiene de "srcRect"
            android.graphics.Rect src = new android.graphics.Rect((int)srcRect._left, (int)srcRect._top,
                    (int)srcRect._right, (int)srcRect._bottom);

            // Crea un rectángulo destino de Java con la información que obtiene de "destRect"
            android.graphics.Rect dest = new android.graphics.Rect((int)destRect._left, (int)destRect._top,
                    (int)destRect._right, (int)destRect._bottom);

            // Crea un objeto Paint que almacenará el valor del alpha
            Paint alphaPaint = new Paint();
            alphaPaint.setAlpha((int)(alpha * 255));

            // Pinta el Bitmap con los rectángulos fuente y destino, y el alpha
            _canvas.drawBitmap(((AImage)image).getImage(), src, dest, alphaPaint);

        }

    } // drawImagePrivate




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Métodos protegidos/privados (de AGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Crea una superficie en la que pintar. Bloquea el canvas, por lo este no podrá ser
     * usado por ningún otro "código" hasta que se desbloquee".
     */
    public void lockCanvas(){

        // Espera mientras que la superficie no sea válida
        while(!_surfaceView.getHolder().getSurface().isValid());

        // Crea una nueva superficie bloqueando el canvas
        _canvas = _surfaceView.getHolder().lockHardwareCanvas();

    } // lockCanvas

    /**
     * Desbloquea el canvas permitiendo a otros su uso.
     */
    public void unlockCanvas(){

        // Desbloquea el canvas
        _surfaceView.getHolder().unlockCanvasAndPost(_canvas);

    } // unlockCanvas




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de AGraphics)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private AssetManager _assetManager = null;
    private SurfaceView _surfaceView = null;
    private Canvas _canvas = null;

} // class AGraphics
