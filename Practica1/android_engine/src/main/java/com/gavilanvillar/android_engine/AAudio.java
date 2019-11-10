package com.gavilanvillar.android_engine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import androidx.appcompat.app.AppCompatActivity;
import com.gavilanvillar.engine.Audio;
import com.gavilanvillar.engine.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AAudio implements Audio
{
    public AAudio(AssetManager assetManager,AppCompatActivity appContext){
        this._assetManager = assetManager;
        this._appContext = appContext;

        this._soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        _soundList = new ArrayList<>();
    }

    @Override
    public Sound newSound(String name)
    {
        AssetFileDescriptor descriptor = null;
        try{
            descriptor  = _assetManager.openFd(name);

        }
        catch (IOException e){
            android.util.Log.e("AAUDIO","FALLO");
        }
        int soundID = _soundPool.load(descriptor, 1);

        Sound s = new ASound(soundID,_soundPool);
        _soundList.add(s);
        return s;
    }

    @Override
    public void muteAll() {
        for(Sound s : _soundList){
            s.mute();
        }
    }

    public void unMuteAll(){

    }
    private AppCompatActivity _appContext;
    private AssetManager _assetManager;
    private SoundPool _soundPool;

    private List<Sound> _soundList;
}
