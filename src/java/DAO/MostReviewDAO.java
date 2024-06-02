package DAO;

import DB.SQLServerConnect;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import model.MostReview;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MostReviewDAO {

    public static MostReviewDAO getInstance() {
        return new MostReviewDAO();
    }

    public ArrayList<MostReview> getMostReview(ServletContext context) throws Exception {

//        ket noi database : 
        DB.SQLServerConnect dbConnect = new SQLServerConnect();
        java.sql.Connection connection = dbConnect.connect(context);

//        tao list : 
        ArrayList<MostReview> reviews = new ArrayList<>();

//        cau truy van : 
        String query = """
            SELECT
                r.Content,
                m.MovieID,
                m.ImageURL,
                m.LinkTrailer,
                m.Title,
                m.Synopsis,
                u.AvatarLink,
                u.Fullname
            FROM
                (SELECT * FROM Review ORDER BY NEWID() OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY) r
            JOIN
                Movie m ON r.MovieID = m.MovieID
            JOIN
                [User] u ON r.UserID = u.UserID;
        """;
//        truy van : 
        try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String content = rs.getString("Content");
                int movieId = rs.getInt("MovieID");
                String imageUrl = rs.getString("ImageURL");
                String linkTrailer = rs.getString("LinkTrailer");
                String title = rs.getString("Title");
                String synopsis = rs.getString("Synopsis");
                String avatarLink = rs.getString("AvatarLink");
                String fullname = rs.getString("Fullname");

                MostReview review = new MostReview(content, movieId, imageUrl, linkTrailer, title, synopsis, avatarLink, fullname);
                reviews.add(review);
            }
        } catch (Exception e) {
        }
        return reviews ; 

    }
}
