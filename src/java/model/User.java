/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author duyqu
 */
public class User {
    private int userID;
    private String avatarLink;
    private int role;
    private String username;
    private String password;
    private String bio;
    private String email;
    private String address;
    private boolean isBanned;
    private int levelPremiumID;
    private float accountBalance;
    private int bonusPoint;

    public User() {
    }

    public User(int userID, String avatarLink, int role, String username, String password, String bio, String email, String address, boolean isBanned, int levelPremiumID, float accountBalance, int bonusPoint) {
        this.userID = userID;
        this.avatarLink = avatarLink;
        this.role = role;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.email = email;
        this.address = address;
        this.isBanned = isBanned;
        this.levelPremiumID = levelPremiumID;
        this.accountBalance = accountBalance;
        this.bonusPoint = bonusPoint;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public int getLevelPremiumID() {
        return levelPremiumID;
    }

    public void setLevelPremiumID(int levelPremiumID) {
        this.levelPremiumID = levelPremiumID;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }
    
    
}
