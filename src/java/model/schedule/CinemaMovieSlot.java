/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schedule;

import java.util.ArrayList;
import java.util.List;
import model.Cinema;
import model.MovieSlot;

/**
 *
 * @author PC
 */
public class CinemaMovieSlot extends Cinema {

    private List<MovieSlot> movieSlots;

    public CinemaMovieSlot() {
        super();
        this.movieSlots = new ArrayList<>();

    }

    public CinemaMovieSlot(int cinemaID, int cinemaChainID,String name, String cinemaAddress, String cinemaProvince, String cinemaDistrict, String cinemaCommune, String cinemaAvatar, List<MovieSlot> movieSlot) {
        super(cinemaID, cinemaChainID, name, cinemaAddress, cinemaProvince, cinemaDistrict, cinemaCommune, cinemaAvatar);
        this.movieSlots = (ArrayList<MovieSlot>) movieSlot;

    }

    public List<MovieSlot> getMovieSlots() {
        return movieSlots;
    }

    public void setMovieSlots(List<MovieSlot> movieSlots) {
        this.movieSlots = movieSlots;
    }

    @Override
    public String toString() {
        return "CinemaMovieSlot{" + "movieSlots=" + movieSlots + '}';
    }

}
