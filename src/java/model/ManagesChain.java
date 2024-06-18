/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author VINHNQ
 */
public class ManagesChain {

    private int userID;
    private int cinemaChainID;

    public ManagesChain() {
    }

    public ManagesChain(int userID, int cinemaChainID) {
        this.userID = userID;
        this.cinemaChainID = cinemaChainID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

}
