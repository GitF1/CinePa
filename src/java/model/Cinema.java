/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class Cinema {
    private int cinemaID;
    private int cinemaChainID;
    private String name;
    private String address;
    private String province;
    private String district;
    private String commune;
    private String avatar;
   

    public Cinema() {
    }

    public Cinema(int cinemaID, int cinemaChainID, String address, String province, String district, String commune) {
        this.cinemaID = cinemaID;
        this.cinemaChainID = cinemaChainID;
        this.address = address;
        this.province = province;
        this.district = district;
        this.commune = commune;
    }

    // Constructor
    public Cinema(int cinemaID, int cinemaChainID, String address, String province, String district, String commune, String avatar) {
        this.cinemaID = cinemaID;
        this.cinemaChainID = cinemaChainID;
        this.address = address;
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.avatar = avatar;
    }
    
    public Cinema(int cinemaID, int cinemaChainID, String name, String address, String province, String district, String commune) {
        this.cinemaID = cinemaID;
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.address = address;
        this.province = province;
        this.district = district;
        this.commune = commune;
      
    }

 

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Cinema{"
                + "cinemaID=" + cinemaID
                + ", cinemaChainID=" + cinemaChainID
                + ", address='" + address + '\''
                + ", province='" + province + '\''
                + ", district='" + district + '\''
                + ", commune='" + commune + '\''
                + ", avatar='" + avatar + '\''
                + '}';
    }

}
