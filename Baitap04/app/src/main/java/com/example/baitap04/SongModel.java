package com.example.baitap04;

import java.io.Serializable;

public class SongModel implements Serializable {
    private String code;
    private String title;
    private String lyric;
    private String artist;

    public SongModel(String code, String title, String lyric, String artist) {
        this.code = code;
        this.title = title;
        this.lyric = lyric;
        this.artist = artist;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getLyric() {
        return lyric;
    }

    public String getArtist() {
        return artist;
    }
}

