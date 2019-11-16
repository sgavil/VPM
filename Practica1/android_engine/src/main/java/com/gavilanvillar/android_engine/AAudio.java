package com.gavilanvillar.android_engine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import androidx.appcompat.app.AppCompatActivity;
import com.gavilanvillar.engine.Audio;
import com.gavilanvillar.engine.Music;
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
        _musicList = new ArrayList<>();
    }

    @Override
    public Sound newSound(String name)
    {
        AssetFileDescriptor descriptor = null;
        try{
            descriptor  = _assetManager.openFd(name);

        }
        catch (IOException e){
            android.util.Log.e("AAUDIO","Error al abrir el archivo de sonido: " + name);
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
        for(Music m : _musicList){
            m.mute();
        }
    }

    public void unMuteAll(){
        for(Sound s : _soundList){
            s.unMute();
        }
        for(Music m : _musicList){
            m.unMute();
        }
    }

    @Override
    public Music newMusic(String name) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());

        AssetFileDescriptor fd = null;

        try {
            fd = _assetManager.openFd(name);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
           mediaPlayer.prepare();

        }
        catch (IOException e) {
            System.out.println("excep in  the music file.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("excep in  the music file.");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("excep in  the music file.");
            e.printStackTrace();
        }
    finally {
            try{
                fd.close();
            }
            catch (Exception e){

            }
        }
        Music m = new AMusic(mediaPlayer);
        _musicList.add(m);
        return m;
    }

    public void stopAll(){
        for(Sound s : _soundList){
            s.stop();
        }
        for(Music m : _musicList){
            m.stop();
        }
    }

    public void resumeAll(){
        for(Sound s : _soundList){
            s.resume();
        }
        for(Music m : _musicList){
            m.resume();
        }
    }

    @Override
    public boolean isSoundMuted() {
        return _isSoundMuted;
    }

    @Override
    public void setSoundState(boolean isSoundMuted) {
        _isSoundMuted = isSoundMuted;
    }


    private AppCompatActivity _appContext;
    private AssetManager _assetManager;
    private SoundPool _soundPool;

    private List<Sound> _soundList;
    private List<Music> _musicList;

    private boolean _isSoundMuted = false;

}
