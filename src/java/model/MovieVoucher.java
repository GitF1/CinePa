/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class MovieVoucher {
    private int movieVoucherID;
    private int userID; // FK to User
    private String type;
    private LocalDate beginDate;
    private LocalDate expiryDate;

    public MovieVoucher() {}

    public MovieVoucher(int movieVoucherID, int userID, String type, LocalDate beginDate, LocalDate expiryDate) {
        this.movieVoucherID = movieVoucherID;
        this.userID = userID;
        this.type = type;
        this.beginDate = beginDate;
        this.expiryDate = expiryDate;
    }

    public int getMovieVoucherID() {
        return movieVoucherID;
    }

    public void setMovieVoucherID(int movieVoucherID) {
        this.movieVoucherID = movieVoucherID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "MovieVoucher{" +
                "movieVoucherID=" + movieVoucherID +
                ", userID=" + userID +
                ", type='" + type + '\'' +
                ", beginDate=" + beginDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
