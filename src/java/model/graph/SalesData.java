/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.graph;

/**
 *
 * @author duyqu
 */
public class SalesData {//can be used for both income data and ticket sold data
    private String date;
    private double valueSold;
    private double ticketSold;
    private String chain;

    public SalesData() {
    }

    public SalesData(String date, double valueSold) {
        this.date = date;
        this.valueSold = valueSold;
    }

    public SalesData(String date, double valueSold,  String chain) {
        this.date = date;
        this.valueSold = valueSold;
        this.chain = chain;
    }

    public SalesData(String date, double valueSold, double ticketSold) {
        this.date = date;
        this.valueSold = valueSold;
        this.ticketSold = ticketSold;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValueSold() {
        return valueSold;
    }

    public void setValueSold(double valueSold) {
        this.valueSold = valueSold;
    }

    public double getTicketSold() {
        return ticketSold;
    }

    public void setTicketSold(double ticketSold) {
        this.ticketSold = ticketSold;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    @Override
    public String toString() {
        return "SalesData{" + "date=" + date + ", valueSold=" + valueSold + ", ticketSold=" + ticketSold + ", chain=" + chain + '}';
    }

   
    
    
}
