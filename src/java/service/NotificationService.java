/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.notification.WebsocketServer;


/**
 *
 * @author PC
 */
public class NotificationService {

    public static void sendNotification(String message, String image, String urlRedirect) {
      
        Gson gson = new Gson();
        JsonObject notificationMessage = new JsonObject();
        notificationMessage.addProperty("url", urlRedirect);
        notificationMessage.addProperty("image", image);
        notificationMessage.addProperty("message", message);
        // Maybe add into database for user offline get it when they access (Alpha)
        //
        WebsocketServer.sendNotification(gson.toJson(notificationMessage));
    }
}
