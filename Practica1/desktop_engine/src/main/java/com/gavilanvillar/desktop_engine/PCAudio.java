package com.gavilanvillar.desktop_engine;

import com.gavilanvillar.engine.Audio;
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
    }

    private List<PCSound> _soundsList;
}
