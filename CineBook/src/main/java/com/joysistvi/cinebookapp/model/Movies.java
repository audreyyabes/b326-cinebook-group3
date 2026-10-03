package com.joysistvi.cinebookapp.model;

public class Movies {

    private int id;
    private String title;
    private String genre;
    private String rating;
    private int duration;

    public Movies() {
    }

    public Movies(int id, String title, String genre, String rating, int duration) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
