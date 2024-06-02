package model;

import java.util.Date;

/**
 *
 * @author duyqu
 */
public class User {
    private int userID;
    private String avatarLink;
    private String fullName;  // Updated field
    private String username;
    private String email;
    private String password;
    private String bio;
    private Date birthday;
    private String address;
    private boolean isBanned;
    private int levelPremiumID;
    private double accountBalance;
    private int bonusPoint;
    private String province;  // Updated field
    private String district;  // Updated field
    private String commune;  // Updated field
    private String code;
    private int status;  // Updated field
    private String role;

    public User() {
    }

    public User(String username, String password, String email, String fullName, String code, int status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.code = code;
        this.status = status;
    }

    public User(String username, String email, String fullName, String code) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.code = code;
    }
    
    
    public User(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public User(String fullName, String username, String email, String password, String code, int status, String role) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.code = code;
        this.status = status;
        this.role = role;
    }

    public User(int userID, String avatarLink, String role, String fullName, String username, String password, String bio, String email, Date birthday, String address, boolean isBanned, int levelPremiumID, double accountBalance, int bonusPoint, String province, String district, String commune, String code, int status) {
        this.userID = userID;
        this.avatarLink = avatarLink;
        this.role = role;
        this.fullName = fullName;  // Updated field
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.isBanned = isBanned;
        this.levelPremiumID = levelPremiumID;
        this.accountBalance = accountBalance;
        this.bonusPoint = bonusPoint;
        this.province = province;  // Updated field
        this.district = district;  // Updated field
        this.commune = commune;  // Updated field
        this.code = code;
        this.status = status;  // Updated field
    }
    
    public User(int userID, String avatarLink, String role, String username, String bio, String email, String fullName, Date birthday, String address, boolean isBanned, int levelPremiumID, double accountBalance, int bonusPoint, String province, String district, String commune) {
        this.userID = userID;
        this.avatarLink = avatarLink;
        this.role = role;
        this.fullName = fullName; 
        this.username = username;
        this.bio = bio;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.isBanned = isBanned;
        this.levelPremiumID = levelPremiumID;
        this.accountBalance = accountBalance;
        this.bonusPoint = bonusPoint;
        this.province = province; 
        this.district = district;  
        this.commune = commune;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {  // Updated getter
        return fullName;
    }

    public void setFullName(String fullName) {  // Updated setter
        this.fullName = fullName;
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

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }

    public String getProvince() {  // Updated getter
        return province;
    }

    public void setProvince(String province) {  // Updated setter
        this.province = province;
    }

    public String getDistrict() {  // Updated getter
        return district;
    }

    public void setDistrict(String district) {  // Updated setter
        this.district = district;
    }

    public String getCommune() {  // Updated getter
        return commune;
    }

    public void setCommune(String commune) {  // Updated setter
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

    public void setStatus(int status) {  // New setter
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", avatarLink='" + avatarLink + '\'' +
                ", role='" + role + '\'' +
                ", name='" + fullName + '\'' +  // Updated toString
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", isBanned=" + isBanned +
                ", levelPremiumID=" + levelPremiumID +
                ", accountBalance=" + accountBalance +
                ", bonusPoint=" + bonusPoint +
                ", province='" + province + '\'' +  // Updated toString
                ", district='" + district + '\'' +  // Updated toString
                ", commune='" + commune + '\'' +  // Updated toString
                ", code='" + code + '\'' +
                ", status=" + status +  // Updated toString
                '}';
    }
}
