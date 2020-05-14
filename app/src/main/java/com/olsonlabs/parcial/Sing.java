package com.olsonlabs.parcial;

public class Sing {
    private String title;
    private String Singer;
    private int Duration;

    public Sing() {
    }

    public Sing(String title, String singer, int duration) {
        this.title = title;
        Singer = singer;
        Duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String singer) {
        Singer = singer;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }
}
