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
                LOGGER.log(Level.INFO, "config.properties not found on classpath; falling back to environment variables.");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error loading config.properties", e);
        }
    }

    private MySQL() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String envUrl = System.getenv("DB_URL");
                String envUser = System.getenv("DB_USER");
                String envPassword = System.getenv("DB_PASSWORD");

                String url = (envUrl != null && !envUrl.isBlank()) ? envUrl : props.getProperty("db.url");
                String user = (envUser != null && !envUser.isBlank()) ? envUser : props.getProperty("db.user");
                String password = (envPassword != null && !envPassword.isBlank()) ? envPassword : props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to MySQL database!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to the database.", e);
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
