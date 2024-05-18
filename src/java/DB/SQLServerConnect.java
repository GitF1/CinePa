package DB;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLServerConnect {

    protected Connection connection;

    public SQLServerConnect() {
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection connect(ServletContext context) throws Exception {

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error loading database configuration", e);
        }

        String serverName = props.getProperty("db.serverName");
        String databaseName = props.getProperty("db.databaseName");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            
            String URLConnect = "jdbc:sqlserver://" + serverName + ";databaseName=" + databaseName + ";user=" + username + ";password=" + password + ";encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URLConnect);

            if (connection == null) {
                throw new Exception("Error When Connecting");
            }

            System.out.println("Connected");
            DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();

            return connection;

        } catch (SQLException e) {
            System.err.println("Cannot connect database, " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        
    }
}
