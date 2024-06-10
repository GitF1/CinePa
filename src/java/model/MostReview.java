package model;

public class MostReview {
    private String content;
    private int movieId;
    private String imageUrl;
    private String linkTrailer;
    private String title;
    private String synopsis;
    private String avatarLink;
    private String fullname;

    // Constructor
    public MostReview(String content, int movieId, String imageUrl, String linkTrailer,
                      String title, String synopsis, String avatarLink, String fullname) {
        this.content = content;
        this.movieId = movieId;
        this.imageUrl = imageUrl;
        this.linkTrailer = linkTrailer;
        this.title = title;
        this.synopsis = synopsis;
        this.avatarLink = avatarLink;
        this.fullname = fullname;
    }

    public MostReview() {
    }
    

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public String toString() {
        return "MostReview{" +
                "content='" + content + '\'' +
                ", movieId=" + movieId +
                ", imageUrl='" + imageUrl + '\'' +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}

