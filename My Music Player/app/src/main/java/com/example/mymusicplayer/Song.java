package com.example.mymusicplayer;

import android.media.MediaPlayer;

public class Song {
    private String name;
    private int id;
    private MediaPlayer mediaPlayer;
    private final String duration;

    public Song(String name, int id, MediaPlayer mediaPlayer) {
        this.name = name;
        this.id = id;
        this.mediaPlayer = mediaPlayer;
        this.duration = this.mediaPlayer.getDuration() / 60000 + ":" + (((this.mediaPlayer.getDuration() % 60000 / 1000) < 10) ? "0" : "")
                + (this.mediaPlayer.getDuration() % 60000) / 1000;
    }

    public String getDuration()
    {
        return duration;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
