/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class CinemaChain {
    
    private int cinemaChainID;
    private String name;
    private String information;
    private String avatar;

    public CinemaChain() {
    }
    
    // Constructor
    public CinemaChain(int cinemaChainID, String name, String information, String avatar) {
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.information = information;
        this.avatar = avatar;
    }

    // Getters and setters
    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "CinemaChain{" +
                "cinemaChainID=" + cinemaChainID +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // Example usage
        CinemaChain cinemaChain = new CinemaChain(1, "AMC Theatres", "AMC Theatres is the largest movie exhibition company in the world.", "path/to/avatar.png");
        System.out.println(cinemaChain);
    }
}
