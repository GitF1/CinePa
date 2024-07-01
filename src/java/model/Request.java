/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
public class Request {
    private int requestID;
    private int userID;
    private String title;
    private String synopsis;
    private String datePublished;
    private String imageURL;
    private double rating;
    private String movieStatus;
    private String country;
    private int length ;//new var, set default to 0 -DuyND
    private String trailerLink ;
    private List<String> genres;
    private String requestStatus;

    public Request(int requestID, int userID, String title, String synopsis, String datePublished, String imageURL, String rating, String movieStatus, String country, int length, String trailerLink, List<String> genres, String requestStatus) {
        this.requestID = requestID;
        this.userID = userID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        if(rating == null) this.rating = 0;
        else this.rating = Float.parseFloat(rating);
        this.movieStatus = movieStatus;
        this.country = country;
        this.length = length;
        this.trailerLink = trailerLink;
        if(genres != null) this.genres = genres;
        else {
            this.genres = new ArrayList<>();
            this.genres.add("Hài hước"); this.genres.add("Bí ẩn"); 
        }
        this.requestStatus = requestStatus;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
    
    

    @Override
    public String toString() {
        return "Request{" + "requestID=" + requestID + ", userID=" + userID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished=" + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", movieStatus=" + movieStatus + ", country=" + country + ", length=" + length + ", trailerLink=" + trailerLink + ", genres=" + genres + ", requestStatus=" + requestStatus + '}';
    }
}
