/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.booking;

import java.sql.Timestamp;

/**
 *
 * @author PC
 */
public class Bill {

    int orderID;
    Timestamp timeCreated;
    int movieSlotID;
    int movieID;    
    int roomID;
    String movieTitle;
    Timestamp startTime;
    Timestamp endTime;

    public Bill() {
          
    }

    public Bill(int orderID, Timestamp timeCreated, int roomID, int movieSlotID, int movieID, String movieTitle, Timestamp startTime, Timestamp endTime) {
        this.orderID = orderID;
        this.timeCreated = timeCreated;
        this.movieSlotID = movieSlotID;
        this.movieID = movieID;
        this.roomID = roomID;
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
