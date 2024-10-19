import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionUtil {
    private static final Logger LOGGER = Logger.getLogger(SessionUtil.class.getName());
    private static int loggedInUserId;

    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static boolean isAdmin() {
        Connection connection = MySQL.getConnection();
        String query = "SELECT is_admin FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, loggedInUserId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_admin");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking whether the user is an admin or not", e);
        } finally {
            MySQL.closeConnection();
        }
        return false;
    }
}