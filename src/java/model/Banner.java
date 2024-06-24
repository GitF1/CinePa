package model;

public class Banner {

    private int bannerID;
    private int cinemaChainID;
    private String bannerImage;

    public Banner(int bannerID, int cinemaChainID, String bannerImage) {
        this.bannerID = bannerID;
        this.cinemaChainID = cinemaChainID;
        this.bannerImage = bannerImage;
    }

    public Banner(int cinemaChainID, String bannerImage) {
        this.cinemaChainID = cinemaChainID;
        this.bannerImage = bannerImage;
    }
    

    public Banner() {
    }

    public int getBannerID() {
        return bannerID;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    
}
