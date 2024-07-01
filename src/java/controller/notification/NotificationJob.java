/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.notification;

// name libary  quat-2.3.2

import DAO.notification.DatabaseNotifier;
import jakarta.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *
 * @author PC
 */
public class NotificationJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            new DatabaseNotifier((ServletContext) context).checkForUpdates();
        } catch (Exception ex) {
            Logger.getLogger(NotificationJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}