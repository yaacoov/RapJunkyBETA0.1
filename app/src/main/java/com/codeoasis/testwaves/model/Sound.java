package com.codeoasis.testwaves.model;

import android.media.MediaPlayer;

/**
 * Created by USER on 8/10/2016.
 */
public class Sound {
    private MediaPlayer sound;
    private int id;

    public Sound(int id, MediaPlayer sound) {
        this.id = id;
        this.sound=sound;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaPlayer getSound() {
        return sound;
    }

    public void setSound(MediaPlayer sound) {
        this.sound = sound;
    }
}
