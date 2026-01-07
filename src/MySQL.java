import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
    private static final Logger LOGGER = Logger.getLogger(MySQL.class.getName());
    private static Connection connection = null;
    private static final Properties props = new Properties();

    static {
        try (InputStream input = MySQL.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.log(Level.SEVERE, "Unable to find config.properties");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading config.properties", e);
        }
    }

    private MySQL() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
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