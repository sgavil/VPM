package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Audio;
import com.gavilanvillar.engine.Music;
import com.gavilanvillar.engine.Sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PCAudio implements Audio {

    public PCAudio() {
        _soundsList = new ArrayList<>();
        _musicList = new ArrayList<>();
    }

    @Override
    public Sound newSound(String name) {

        Clip clip = null;
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported. " + name);
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file. " + name);
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable. " + name);
            ex.printStackTrace();
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                System.out.println("Could not close audioInputStream.");
                ex.printStackTrace();
            }
        }
        PCSound s = new PCSound(clip);
        _soundsList.add(s);
        return s;
    }

    @Override
    public void muteAll() {
        for(PCSound s : _soundsList)
        {
            s.mute();
        }
        for(Music m : _musicList){
            m.mute();
        }
    }

    @Override
    public void unMuteAll() {
        for(PCSound s : _soundsList)
        {
            s.unMute();
        }
        for(Music m : _musicList){
            m.unMute();
        }
    }

    @Override
    public Music newMusic(String name) {
        Clip clip = null;
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } finally {
            try {
                audioInputStream.close();
            } catch (IOException ex) {
                System.out.println("Could not close audioInputStream.");
                ex.printStackTrace();
            }
        }
        PCMusic s = new PCMusic(clip);
        _musicList.add(s);
        return s;
    }

    @Override
    public void stopAll() {
        for(Music m : _musicList){
            m.stop();
        }
        for(Sound s : _soundsList){
            s.stop();
        }
    }

    @Override
    public void resumeAll() {
        for(Music m : _musicList){
            m.resume();
        }
        for(Sound s : _soundsList){
            s.resume();
        }
    }
    @Override
    public boolean isSoundMuted(){
        return _isSoundMuted;
    }
    @Override
    public void setSoundState(boolean isSoundMuted){
        _isSoundMuted = isSoundMuted;
    }

    private List<PCSound> _soundsList;
    private List<PCMusic> _musicList;

    private boolean _isSoundMuted = false;
 }
