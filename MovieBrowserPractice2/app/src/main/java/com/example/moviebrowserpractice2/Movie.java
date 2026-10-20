package com.example.moviebrowserpractice2;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String director;
    private int year;
    private String category;

    public Movie(String title, String director, int year, String category) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }
}