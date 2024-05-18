/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;

public class User {
    private int userID;
    private String avatarLink;
    private String role;
    private String username;
    private String password;
    private String bio;
    private String email;
    private String fullname;
    private Date birthday;
    private String address;
    private boolean isBanned;
    private int levelPremiumID;
    private float accountBalance;
    private int bonusPoint;
    private String province;
    private String district;
    private String commune;
    private String code;
    private int status;

    // Constructor

    public User() {
    }
    
    
    public User(int userID, String avatarLink, String role, String username, String password, String bio, String email, String fullname, Date birthday, String address, boolean isBanned, int levelPremiumID, float accountBalance, int bonusPoint, String province, String district, String commune, String code, int status) {
        this.userID = userID;
        this.avatarLink = avatarLink;
        this.role = role;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.address = address;
        this.isBanned = isBanned;
        this.levelPremiumID = levelPremiumID;
        this.accountBalance = accountBalance;
        this.bonusPoint = bonusPoint;
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.code = code;
        this.status = status;
    }

    // Getters and setters
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

