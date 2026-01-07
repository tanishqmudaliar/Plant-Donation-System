import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationPage extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(RegistrationPage.class.getName());
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField mobileField;
    private JComboBox<String> dobDay;
    private JComboBox<String> dobMonth;
    private JComboBox<String> dobYear;
    private JRadioButton male;
    private JRadioButton female;
    private JPasswordField passwordField;
    private JCheckBox isAdmin;

    public RegistrationPage() {
        setTitle("Registration Page");
        setMinimumSize(new Dimension(800, 800));
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
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 35));
        nameField.setMargin(new Insets(0, 5, 0, 5));
        nameField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 35));
        emailField.setMargin(new Insets(0, 5, 0, 5));
        emailField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel mobileLabel = new JLabel("Mobile");
        mobileLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(mobileLabel, gbc);

        gbc.gridx = 1;
        mobileField = new JTextField();
        mobileField.setPreferredSize(new Dimension(250, 35));
        mobileField.setMargin(new Insets(0, 5, 0, 5));
        mobileField.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel dobLabel = new JLabel("Date of Birth");
        dobLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(dobLabel, gbc);

        gbc.gridx = 1;
        Dimension fieldSize = new Dimension(80, 35);

        dobDay = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        dobDay.setPreferredSize(fieldSize);
        dobDay.setRenderer(new CenteredComboBoxRenderer());

        dobMonth = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        dobMonth.setPreferredSize(fieldSize);
        dobMonth.setRenderer(new CenteredComboBoxRenderer());

        String[] years = new String[2015 - 1925 + 1];
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(1925 + i);
        }
        dobYear = new JComboBox<>(years);
        dobYear.setPreferredSize(fieldSize);
        dobYear.setRenderer(new CenteredComboBoxRenderer());

        JPanel dobPanel = new JPanel();
        dobPanel.add(dobDay);
        dobPanel.add(dobMonth);
        dobPanel.add(dobYear);

        panel.add(dobPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(genderLabel, gbc);

        gbc.gridx = 1;
        JPanel genderPanel = new JPanel();
        male = new JRadioButton("Male");
        male.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        female = new JRadioButton("Female");
        female.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        genderPanel.add(male);
        genderPanel.add(female);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        panel.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
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
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        isAdmin = new JCheckBox("Register as Admin");
        isAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(isAdmin, gbc);

        gbc.gridy = 8;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        registerButton.setPreferredSize(new Dimension(371, 35));
        panel.add(registerButton, gbc);

        gbc.gridy = 9;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Roboto", Font.PLAIN, 16));
        backButton.setPreferredSize(new Dimension(371, 35));
        panel.add(backButton, gbc);

        registerButton.addActionListener(e -> registerUser());

        backButton.addActionListener(e -> {
            new LoginPage();
            dispose();
        });
    }

    private void registerUser() {
        String username = usernameField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String mobile = mobileField.getText();
        String dob = dobYear.getSelectedItem() + "-" + dobMonth.getSelectedItem() + "-" + dobDay.getSelectedItem();
        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
        String password = new String(passwordField.getPassword());
        boolean admin = isAdmin.isSelected();

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || mobile.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill every field.");
            return;
        }

        if (!mobile.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Mobile number must contain only numbers.");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email must contain '@'.");
            return;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
            return;
        }

        Connection connection = MySQL.getConnection();
        String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
        String insertQuery = "INSERT INTO users (username, name, email, mobile, dob, gender, password, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setString(1, username);
            checkStatement.setString(2, email);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Username or email already exists.");
                return;
            }

            try {
                password = EncryptionUtil.encrypt(password);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error encrypting password.", e);
                JOptionPane.showMessageDialog(this, "Error encrypting password.");
                return;
            }

            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, name);
                insertStatement.setString(3, email);
                insertStatement.setString(4, mobile);
                insertStatement.setString(5, dob);
                insertStatement.setString(6, gender);
                insertStatement.setString(7, password);
                insertStatement.setBoolean(8, admin);

                insertStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                new LoginPage();
                dispose();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering user.", e);
            JOptionPane.showMessageDialog(this, "Error registering user.");
        } finally {
            MySQL.closeConnection();
        }
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}