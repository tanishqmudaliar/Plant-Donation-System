import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationPage extends JFrame {
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
    private JButton backButton;

    public RegistrationPage() {
        setTitle("Registration Page");
        setSize(500, 500);
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
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Name");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email");
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel mobileLabel = new JLabel("Mobile");
        panel.add(mobileLabel, gbc);

        gbc.gridx = 1;
        mobileField = new JTextField(20);
        panel.add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel dobLabel = new JLabel("Date of Birth");
        panel.add(dobLabel, gbc);

        gbc.gridx = 1;
        JPanel dobPanel = new JPanel();
        dobPanel.add(dobDay = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
        dobPanel.add(dobMonth = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
        dobPanel.add(dobYear = new JComboBox<>(new String[]{"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010"}));
        panel.add(dobPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel genderLabel = new JLabel("Gender");
        panel.add(genderLabel, gbc);

        gbc.gridx = 1;
        JPanel genderPanel = new JPanel();
        genderPanel.add(male = new JRadioButton("Male"));
        genderPanel.add(female = new JRadioButton("Female"));
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        panel.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel passwordLabel = new JLabel("Password");
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        isAdmin = new JCheckBox("Register as Admin");
        panel.add(isAdmin, gbc);

        gbc.gridy = 8;
        JButton registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        gbc.gridy = 9;
        backButton = new JButton("Back");
        panel.add(backButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
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

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || mobile.isEmpty() || dob.isEmpty() || gender.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill every field.");
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
                e.printStackTrace();
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user.");
        } finally {
            MySQL.closeConnection();
        }
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}