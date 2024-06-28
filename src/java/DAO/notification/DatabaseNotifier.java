/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.notification;

import DB.SQLServerConnect;
import controller.notification.WebsocketServer;
import jakarta.servlet.ServletContext;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author PC
 */
public class DatabaseNotifier extends SQLServerConnect {

    public DatabaseNotifier(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public  void checkForUpdates() {
        try {
           
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Movie WHERE condition");

            while (rs.next()) {
                String message = "New update: " + rs.getString("column");
                WebsocketServer.sendNotification(message);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
