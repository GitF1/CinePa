package model.booking;
        
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class TempOrderDetails {

    private List<Integer> seatIDs;
    private List<CanteenItemOrder> canteenOrderItems;
    private int movieSlotID;
    public TempOrderDetails(List<Integer> seatIDs, List<CanteenItemOrder> canteenOrderItems,int movieSlotID) {
        this.seatIDs = seatIDs;
        this.canteenOrderItems = canteenOrderItems;
        this.movieSlotID = movieSlotID;
    }

    public List<Integer> getSeatIDs() {
        return seatIDs;
    }

    public List<CanteenItemOrder> getCanteenOrderItems() {
        return canteenOrderItems;
    }

    public int getMovieSlotID() {
        return movieSlotID;
    }
    
}
