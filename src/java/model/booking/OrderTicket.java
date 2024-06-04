/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.booking;

import java.util.List;

/**
 *
 * @author PC
 */
public class OrderTicket {

    private int userID;
    private int movieSlotID;
    private List<Integer> seatsID;
    private List<CanteenItemOrder> canteenItemOrders;
    private double totalPriceTicket;
    private double totalPriceCanteen;
    private String movieName;
    private String slotMovie;
    private String date;
    private String roomName;
    private String roomType;
    
    
    //
    
    

    public OrderTicket() {
    }

    public OrderTicket(int userID, int movieSlotID, List<Integer> seatsID, List<CanteenItemOrder> canteenItemOrders) {
        this.userID = userID;
        this.movieSlotID = movieSlotID;
        this.seatsID = seatsID;
        this.canteenItemOrders = canteenItemOrders;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSlotMovie() {
        return slotMovie;
    }

    public void setSlotMovie(String slotMovie) {
        this.slotMovie = slotMovie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPriceTicket() {
        return totalPriceTicket;
    }

    public void setTotalPriceTicket(double totalPriceTicket) {
        this.totalPriceTicket = totalPriceTicket;
    }

    public double getTotalPriceCanteen() {
        return totalPriceCanteen;
    }

    public void setTotalPriceCanteen(double totalPriceCanteen) {
        this.totalPriceCanteen = totalPriceCanteen;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public List<Integer> getSeatsID() {
        return seatsID;
    }

    public void setSeatsID(List<Integer> seatsID) {
        this.seatsID = seatsID;
    }

    public List<CanteenItemOrder> getCanteenItemOrders() {
        return canteenItemOrders;
    }

    public void setCanteenItemOrders(List<CanteenItemOrder> canteenItemOrders) {
        this.canteenItemOrders = canteenItemOrders;
    }

    @Override
    public String toString() {
        return "OrderTicket{" + "userID=" + userID + ", movieSlotID=" + movieSlotID + ", seatsID=" + seatsID + ", canteenItemOrders=" + canteenItemOrders + '}';
    }
    
    

}
