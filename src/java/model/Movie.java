package model;

import java.util.List;


/**
 *
 * @author Admin
 */
public class Movie {

    public int movieID;
    public String title;
    public String synopsis;
    public String datePublished;
    public String imageURL;
    public double rating;
    public String status;
    public String country;
    public int length ;//new var, set default to 0 -DuyND
    public String trailerLink ;
    public List<String> genres;

    public Movie() {
    }


    public Movie(int movieID, String title, String synopsis, String datePublished, String imageURL, double rating, String status, String country, int length, String trailerLink) {//new constructor with trailer and length
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.status = status;
        this.country = country;
        this.length = length;
        this.trailerLink = trailerLink;
    }

    public Movie(int movieID, String title, String synopsis, String datePublished, String imageURL, double rating, String status, String country) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.status = status;
        this.country = country;
    }

    public Movie(int movieID, String title, String synopsis, String datePublished, String imageURL, double rating, String status, String country, List<String> genres) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.status = status;
        this.country = country;
        this.genres = genres;
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
        String ans = String.format("%,.2f", rating);
        rating = Double.parseDouble(ans);
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    
    

    @Override
    public String toString() {
        return "Movie{" + "movieID=" + movieID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished=" + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", country=" + country + ", status=" + status + '}';
    }
    
    public String toString2() {
        return "Movie{" + "movieID=" + movieID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished=" + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", country=" + country + ", status=" + status + '\n' +  genres + '}';
    }

    
}
