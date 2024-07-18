package model;

import java.time.LocalDateTime;

public class Order {

    private int userID;
    private LocalDateTime timeCreated;
    private String status;
    private String QRCodeURL;
    private String Code;

    public Order() {
    }
    
    
    public Order(int userID, LocalDateTime timeCreated, String status) {
        this.userID = userID;
        this.timeCreated = timeCreated;
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQRCodeURL() {
        return QRCodeURL;
    }

    public void setQRCodeURL(String QRCodeURL) {
        this.QRCodeURL = QRCodeURL;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }
    

}
