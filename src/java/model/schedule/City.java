/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schedule;

/**
 *
 * @author PC
 */
public class City {

    private String name;
    private double latitude;
    private double longitude;
    private double distance;

    public City() {
    }
    
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "City{" + "name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", distance=" + distance + '}';
    }
    
}
