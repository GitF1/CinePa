/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class Seat {
    private int seatID;
    private int roomID;
    private String name;
    private int x;
    private int y;
    private String status;
    
    public Seat() {
        
    }
    
    public Seat(int seatID) {
        this.seatID = seatID;
    }

    public Seat(int seatID, int roomID, String name, int x, int y) {
        this.seatID = seatID;
        this.roomID = roomID;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public Seat(int seatID, int roomID, String name, int x, int y, String status) {
        this.seatID = seatID;
        this.roomID = roomID;
        this.name = name;
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "Java: Seat{" + "seatID=" + seatID + ", roomID=" + roomID + ", name=" + name + ", x=" + x + ", y=" + y + ", status=" + status + '}';
    }

    
}
