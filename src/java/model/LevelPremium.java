/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author VINHNQ
 */
public class LevelPremium {
    private int levelPremiumID;
    private String premiumName;
    private double conditionPrice;
    private double movieBonusRate;
    private double canteenBonusRate;

    // Default constructor
    public LevelPremium() {}

    // Parameterized constructor
    public LevelPremium(int levelPremiumID, String premiumName, double conditionPrice, double movieBonusRate, double canteenBonusRate) {
        this.levelPremiumID = levelPremiumID;
        this.premiumName = premiumName;
        this.conditionPrice = conditionPrice;
        this.movieBonusRate = movieBonusRate;
        this.canteenBonusRate = canteenBonusRate;
    }

    // Getter and Setter methods

    public int getLevelPremiumID() {
        return levelPremiumID;
    }

    public void setLevelPremiumID(int levelPremiumID) {
        this.levelPremiumID = levelPremiumID;
    }

    public String getPremiumName() {
        return premiumName;
    }

    public void setPremiumName(String premiumName) {
        this.premiumName = premiumName;
    }

    public double getConditionPrice() {
        return conditionPrice;
    }

    public void setConditionPrice(double conditionPrice) {
        this.conditionPrice = conditionPrice;
    }

    public double getMovieBonusRate() {
        return movieBonusRate;
    }

    public void setMovieBonusRate(double movieBonusRate) {
        this.movieBonusRate = movieBonusRate;
    }

    public double getCanteenBonusRate() {
        return canteenBonusRate;
    }

    public void setCanteenBonusRate(double canteenBonusRate) {
        this.canteenBonusRate = canteenBonusRate;
    }

    @Override
    public String toString() {
        return "LevelPremium{" +
                "levelPremiumID=" + levelPremiumID +
                ", premiumName='" + premiumName + '\'' +
                ", conditionPrice=" + conditionPrice +
                ", movieBonusRate=" + movieBonusRate +
                ", canteenBonusRate=" + canteenBonusRate +
                '}';
    }
}

