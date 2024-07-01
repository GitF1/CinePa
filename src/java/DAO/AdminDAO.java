/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import jakarta.servlet.ServletContext;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class AdminDAO extends UserDAO {
    
    public AdminDAO(ServletContext context) throws Exception {
        super(context);
    }
    
    public int getNumberOfPendingRequests() throws SQLException {
        String sqlQuery = "select count(*) as PendingRequest from MovieApproval\n" +
                    "where RequestStatus = 'PENDING'";
        ResultSet rs = super.getResultSet(sqlQuery);
        return rs.getInt("PendingRequests");
    }
    
}
