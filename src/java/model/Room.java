/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Room {
    private int roomID;
    private int cinemaID;
    private String name;
    private String type;
    private int capacity;
    private int length;
    private int width;
    private String status;

    // Constructor
    public Room(int roomID, int cinemaID, String name, String type, int capacity, String status) {
        this.roomID = roomID;
        this.cinemaID = cinemaID;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
    }
    
    public Room(int roomID, int cinemaID, String name, String type, int capacity, int length, int width, String status) {
        this.roomID = roomID;
        this.cinemaID = cinemaID;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.length = length;
        this.width = width;
        this.status = status;
    }

    // Default constructor
    public Room() {
    }

    // Getter and Setter methods
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", cinemaID=" + cinemaID + ", name=" + name + ", type=" + type + ", capacity=" + capacity + ", length=" + length + ", width=" + width + ", status=" + status + '}';
    }
}

