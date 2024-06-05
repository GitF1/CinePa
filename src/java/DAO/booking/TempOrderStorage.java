/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.booking;

import java.util.concurrent.ConcurrentHashMap;
import model.booking.TempOrderDetails;

/**
 *
 * @author PC
 */
public class TempOrderStorage {

    private static ConcurrentHashMap<Integer, TempOrderDetails> tempOrders = new ConcurrentHashMap<>();

    public static void storeTempOrderDetails(int orderID, TempOrderDetails details) {
        tempOrders.put(orderID, details);
    }

    public static TempOrderDetails getTempOrderDetails(int orderID) {
        return tempOrders.get(orderID);
    }

    public static void removeTempOrderDetails(int orderID) {
        tempOrders.remove(orderID);
    }
}
