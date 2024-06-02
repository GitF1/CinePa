package model;

public class MovieInGenre {
    private int movieID;
    private String genre;

    public MovieInGenre(int movieID, String genre) {
        this.movieID = movieID;
        this.genre = genre;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "MovieInGenre{" + "movieID=" + movieID + ", genre=" + genre + '}';
    }

    
}