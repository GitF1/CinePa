/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;

/**
 *
 * @author Admin
 */
public class UserDAO extends SQLServerConnect {

    public UserDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
    
        
}
