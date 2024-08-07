
package model.movie;
import java.util.Date;
import java.util.List;
import model.Movie;

public class MovieInfo  {
    private int movieID;
    private int cinemaID;
    private String title;
    private Date datePublished;
    private float rating;
    private String imageURL;
    private String synopsis;
    private String country;
    private int year;
    private int length;
    private String linkTrailer;
    private List<String> genres;
    private String status ; 

    // Constructor
    public MovieInfo() {
        
    }
    
    
    public MovieInfo(int movieID, int cinemaID, String title, Date datePublished, float rating, String imageURL,
                 String synopsis, String country, int year, int length, String linkTrailer, List<String> genres) {
        this.movieID = movieID;
        this.cinemaID = cinemaID;
        this.title = title;
        this.datePublished = datePublished;
        this.rating = rating;
        this.imageURL = imageURL;
        this.synopsis = synopsis;
        this.country = country;
        this.year = year;
        this.length = length;
        this.linkTrailer = linkTrailer;
        this.genres = genres;
    }
    // lay ra chuoi cac the loai : 
     public String getGenresAsString() {
        return String.join(", ", genres);
    }
    
    
   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and setters
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
