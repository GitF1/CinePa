/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Seat {
    private int seatID;
    private String name;
    private int roomID;
    private int coordinateX;
    private int coordinateY;

    public Seat() {
    }

    public Seat(String name, int roomID, int coordinateX, int coordinateY) {
        this.name = name;
        this.roomID = roomID;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public Seat(int seatID, String name, int roomID, int coordinateX, int coordinateY) {
        this.seatID = seatID;
        this.name = name;
        this.roomID = roomID;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }
    
    public String getName() {
        return name;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    @Override
    public String toString() {
        return "Seat{" + "seatID=" + seatID + ", name=" + name + ", roomID=" + roomID + ", coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + '}';
    }
    
    
}
