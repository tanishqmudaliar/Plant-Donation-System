import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class.getName());
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login");
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 35));
        usernameField.setMargin(new Insets(0, 5, 0, 5));
        usernameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setMargin(new Insets(0, 5, 0, 5));
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        loginButton.setPreferredSize(new Dimension(351, 35));
        panel.add(loginButton, gbc);

        gbc.gridy = 3;

        JLabel createAccountLabel = new JLabel("<html><a href=''>Create Account</a></html>");
        createAccountLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(createAccountLabel, gbc);

        loginButton.addActionListener(e -> loginUser());

        createAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new RegistrationPage();
                dispose();
            }
        });
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Connection connection = MySQL.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String encryptedPassword = resultSet.getString("password");
                String decryptedPassword = EncryptionUtil.decrypt(encryptedPassword); // Decrypt the password

                if (password.equals(decryptedPassword)) {
                    int userId = resultSet.getInt("id"); // Assuming the user ID is stored in the "id" column
                    SessionUtil.setLoggedInUserId(userId); // Store the logged-in user ID
                    new HomePage(); // Redirect to home page
                    dispose(); // Close the login page
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error logging in.", e);
            JOptionPane.showMessageDialog(this, "Error logging in.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting password.", e);
            JOptionPane.showMessageDialog(this, "Error decrypting password.");
        } finally {
            MySQL.closeConnection();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}