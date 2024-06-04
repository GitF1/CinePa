/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovieSlot {

    private int movieSlotID;
    private int roomID;
    private int movieID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String type;
    private float price;
    private float discount;
    private String status;

    // Constructor
    public MovieSlot(int movieSlotID, int roomID, int movieID, LocalDateTime startTime, LocalDateTime endTime, String type, float price, float discount, String status) {
        this.movieSlotID = movieSlotID;
        this.roomID = roomID;
        this.movieID = movieID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.price = price;
        this.discount = discount;
        this.status = status;
    }

    // Default constructor
    public MovieSlot() {
    }

    // Getter and Setter methods
    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return startTime.format(dateFormatter);
    }

    public String getFormattedStartTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return startTime.format(timeFormatter);
    }

    public String getFormattedEndTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return endTime.format(timeFormatter);
    }

    @Override
    public String toString() {
        return "MovieSlot{"
                + "movieSlotID=" + movieSlotID
                + ", roomID=" + roomID
                + ", movieID=" + movieID
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", type='" + type + '\''
                + ", price=" + price
                + ", discount=" + discount
                + ", status=" + status
                + '}';
    }
}
