/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class Movie {

    private int movieID;
    private String title;
    private String synopsis;
    private String datePublished;
    private String imageURL;
    private double rating;

    public Movie() {
    }

    // Constructor
    public Movie(int movieID, String title, String synopsis, String datePublished, String imageURL, double rating) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
    }

    // Getter and Setter methods
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{"
                + "movieID=" + movieID
                + ", title='" + title + '\''
                + ", synopsis='" + synopsis + '\''
                + ", datePublished='" + datePublished + '\''
                + ", imageURL='" + imageURL + '\''
                + ", rating=" + rating
                + '}';
    }
}
