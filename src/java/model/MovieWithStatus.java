
package model;

/**
 *
 * @author Admin
 */
public class MovieWithStatus {
    private int movieID;
    private String title;
    private String synopsis;
    private String datePublished;
    private String imageURL;
    private double rating;
    private String status;
    private String country;

    // Constructor

    public MovieWithStatus(int movieID, String title, String synopsis, String datePublished, String imageURL, double rating, String status, String country) {
        this.movieID = movieID;
        this.title = title;
        this.synopsis = synopsis;
        this.datePublished = datePublished;
        this.imageURL = imageURL;
        this.rating = rating;
        this.status = status;
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    

    // Default constructor
    public MovieWithStatus() {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Movie{" + "movieID=" + movieID + ", title=" + title + ", synopsis=" + synopsis + ", datePublished=" + datePublished + ", imageURL=" + imageURL + ", rating=" + rating + ", country=" + country + '}';
    }

    
}