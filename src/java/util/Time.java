/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class Time {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    // Constructor to initialize with current date and time
    public Time() {
        LocalDateTime now = LocalDateTime.now();
        this.year = now.getYear();
        this.month = now.getMonthValue();
        this.day = now.getDayOfMonth();
        this.hour = now.getHour();
        this.minute = now.getMinute();
    }

    // Getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public static void main(String[] args) {
        // Create an instance of CurrentDateTime to get current date and time
        Time currentDateTime = new Time();

        // Print current date, month, year, hour, and minute
       
        System.out.println("Current Hour: " + currentDateTime.getHour() );
    }
}
