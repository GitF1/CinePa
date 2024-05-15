package model;


public class Ticket {
    private int movieSlot , orderID , seatID ; 
    private String status ; 

    public Ticket(int movieSlot, int orderID, int seatID, String status) {
        this.movieSlot = movieSlot;
        this.orderID = orderID;
        this.seatID = seatID;
        this.status = status;
    }

    public int getMovieSlot() {
        return movieSlot;
    }

    public void setMovieSlot(int movieSlot) {
        this.movieSlot = movieSlot;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
