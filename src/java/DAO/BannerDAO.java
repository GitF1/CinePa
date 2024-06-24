package DAO;

import DB.SQLServerConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import model.Banner;

public class BannerDAO extends SQLServerConnect {

    public BannerDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public void updateBanner(Banner banner) {
        String updateSql = "UPDATE BannerTable SET BannerImage = ? WHERE CinemaChainID = ?";
        String insertSql = "INSERT INTO BannerTable (CinemaChainID, BannerImage) VALUES (?, ?)";
        
        try {
            // First, try to update the banner
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setString(1, banner.getBannerImage());
            updateStmt.setInt(2, banner.getCinemaChainID());
            int rowsUpdated = updateStmt.executeUpdate();
            
            // If no rows were updated, it means the record doesn't exist, so insert instead
            if (rowsUpdated == 0) {
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, banner.getCinemaChainID());
                insertStmt.setString(2, banner.getBannerImage());
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getCinemaChainIDByBannerID(int bannerID) {
        int cinemaChainID = 0;
        String sql = "SELECT CinemaChainID FROM BannerTable WHERE BannerID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, bannerID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cinemaChainID = rs.getInt("CinemaChainID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChainID;
    }
    
 
}


