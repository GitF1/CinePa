/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schedule;

import java.util.List;
import model.Cinema;
import model.CinemaChain;


/**
 *
 * @author PC
 */
public class Schedule {

    private String city;
    private String cinemaID;
    private String branchID;
    private String selectDate;

    private CinemaChain cinema;
    private Cinema branch;

    private List<Cinema> listBranch;
    private List<MovieSchedule> listMovie;
    private List<DateInfo> listShowDate;

    public List<DateInfo> getListShowDate() {
        return listShowDate;
    }

    public void setListShowDate(List<DateInfo> listShowDate) {
        this.listShowDate = listShowDate;
    }

    public Schedule() {
    }

    public Schedule(String city, CinemaChain cinema, Cinema branch, List<Cinema> listBranch, List<MovieSchedule> listMovie) {
        this.city = city;
        this.cinema = cinema;
        this.branch = branch;
        this.listBranch = listBranch;
        this.listMovie = listMovie;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CinemaChain getCinema() {
        return cinema;
    }

    public void setCinema(CinemaChain cinema) {
        this.cinema = cinema;
    }

    public Cinema getBranch() {
        return branch;
    }

    public void setBranch(Cinema branch) {
        this.branch = branch;
    }

    public List<Cinema> getListBranch() {
        return listBranch;
    }

    public void setListBranch(List<Cinema> listBranch) {
        this.listBranch = listBranch;
    }

    public List<MovieSchedule> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<MovieSchedule> listMovie) {
        this.listMovie = listMovie;
    }

}
