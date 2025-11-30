import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
    private static final Logger LOGGER = Logger.getLogger(MySQL.class.getName());
    private static Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/plant_donation";
    private static final String USER = "root";
    private static final String PASSWORD = "tanishq11";

    private MySQL() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to MySQL database!");
            } catch (SQLException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error connecting to the database.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing the database connection.", e);
            }
        }
    }
}