/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author duyqu
 */
public class MovieSlot2 {//mainly to be parsed as json file

    private String title;//currently set as movieID
    private Date start;
    private Date end;
    private String color = "rgb(13,110,253)";
    private int id = 1;

//    Because localdatetime does not parse to json, do not question my decisions - DuyND
    public MovieSlot2(int movieSlotID, int roomID, int movieID, LocalDateTime startTime, LocalDateTime endTime, String type, float price, float discount, String status) {

        this.title = String.valueOf(movieID);
        this.start = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        this.end = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

    }

    public MovieSlot2(int movieSlotID, int roomID, int movieID, LocalDateTime startTime, LocalDateTime endTime, String type, float price, float discount, String status, int id) {

        this.title = String.valueOf(movieID);
        this.start = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        this.end = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        this.id = id;
    }

    public MovieSlot2() {
    }

    public MovieSlot2(MovieSlot ms) {
        this.id=ms.getMovieSlotID();
        this.title = String.valueOf(ms.getMovieID());
        this.start = Date.from(ms.getStartTime().atZone(ZoneId.of("Etc/GMT0")).toInstant());
        this.end = Date.from(ms.getEndTime().atZone(ZoneId.of("Etc/GMT0")).toInstant());

    }

    public MovieSlot2(int movieSlotID, int roomID, int movieID, Date startTime, Date endTime, String type, float price, float discount, String status) {

        this.title = String.valueOf(movieID);
        this.start = startTime;
        this.end = endTime;

    }

    public String toJson() {
        // Custom JsonSerializer for Date
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

        return gson.toJson(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "MovieSlot2{" + "title=" + title + ", start=" + start + ", end=" + end + '}';
    }

    public static void main(String[] args) {
        MovieSlot ms = new MovieSlot(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(), "1", 1, 1, "1");

        MovieSlot2 ms2 = new MovieSlot2(ms);
        System.out.println(ms2.toJson());
        for (String s : ZoneId.getAvailableZoneIds()) {
            System.out.println(s);
            System.out.println(ZoneId.systemDefault());
            System.out.println("-");
            System.out.println(ZoneId.of("Etc/GMT0"));
        }

    }
}
