
package com.example.cinema76.models;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String description;
    private String cinemaName;
    private String posterUrl;

    public Movie() {}

    public Movie(String id, String title, String description, String cinemaName, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cinemaName = cinemaName;
        this.posterUrl = posterUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCinemaName() { return cinemaName; }
    public void setCinemaName(String cinemaName) { this.cinemaName = cinemaName; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}