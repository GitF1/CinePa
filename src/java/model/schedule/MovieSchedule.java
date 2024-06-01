/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schedule;

import java.util.List;
import model.Movie;
import model.MovieSlot;

/**
 *
 * @author PC
 */
public class MovieSchedule extends Movie {

    private List<MovieSlot> listMovieSlot;

    public MovieSchedule() {
        super();
    }
    
    
    public List<MovieSlot> getListMovieSlot() {
        return listMovieSlot;
    }

    public void setListMovieSlot(List<MovieSlot> listMovieSlot) {
        this.listMovieSlot = listMovieSlot;
    }

}
