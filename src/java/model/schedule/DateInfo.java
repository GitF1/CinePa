/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schedule;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class DateInfo {

    private String date;
    private String dayOfWeek;
    private String time;

    public DateInfo() {
    }



    public DateInfo(String date, String dayOfWeek, String time) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String getDayOfWeekInVietnamese(String dayOfWeek) {
        switch (dayOfWeek) {
            case "MONDAY":
                return "Thứ hai";
            case "TUESDAY":
                return "Thứ ba";
            case "WEDNESDAY":
                return "Thứ tư";
            case "THURSDAY":
                return "Thứ năm";
            case "FRIDAY":
                return "Thứ sáu";
            case "SATURDAY":
                return "Thứ bảy";
            case "SUNDAY":
                return "Chủ nhật";
            default:
                return "";
        }
    }

    public List<DateInfo> generateWeek() {
        List<DateInfo> week = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd", new Locale("vi"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",new Locale("vi"));

        for (int i = 0; i < 7; i++) {

            String formattedDate = today.format(dateFormatter);
            // Format the date as "day-month-year"
            String formattedTime = today.format(timeFormatter);
            // Set the time part to "00:00:00"
//            LocalDateTime dateTime = LocalDateTime.parse(today.toString() + " 00:00:00", timeFormatter);
//            Timestamp timestamp = Timestamp.valueOf(dateTime);
            // Add date, day of week, and time to the list
            week.add(new DateInfo(formattedDate, getDayOfWeekInVietnamese(today.getDayOfWeek().toString()), formattedTime.toString()));
            // Move to the next day
            today = today.plusDays(1);
        }

        return week;
    }

    @Override
    public String toString() {
        return date + "-" + dayOfWeek;
    }

    public static void main(String[] args) {
        DateInfo d = new DateInfo();
        List<DateInfo> weekList = d.generateWeek();
        for (DateInfo day : weekList) {
            System.out.println(day.date + " - " + day.dayOfWeek +" (" +day.getTime()+")");
        }
    }

}
