import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePage extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(HomePage.class.getName());
    private final JFrame mainFrame;

    public HomePage() {
        mainFrame = this;
        setTitle("Leaf Legacy - Plant donation and buying system");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null);
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        Font componentFont = new Font("Times New Roman", Font.PLAIN, 20);

        ImageIcon headframe = new ImageIcon("src/Assets/heading.png");
        JLabel heading = new JLabel(headframe);
        heading.setBounds(0, 0, 800, 100);
        panel.add(heading);

        ImageIcon donateIcon = new ImageIcon("src/Assets/donate.png");
        JLabel donateLabel = new JLabel(donateIcon);
        donateLabel.setBounds(50, 150, 300, 300);
        panel.add(donateLabel);

        ImageIcon buyIcon = new ImageIcon("src/Assets/buy.png");
        JLabel buyLabel = new JLabel(buyIcon);
        buyLabel.setBounds(450, 150, 300, 300);
        panel.add(buyLabel);

        JButton donateButton = new JButton("Donate now!");
        donateButton.setBounds(50, 470, 300, 50);
        donateButton.setFont(componentFont);
        panel.add(donateButton);

        JButton orderButton = new JButton("Order now!");
        orderButton.setBounds(450, 470, 300, 50);
        orderButton.setFont(componentFont);
        panel.add(orderButton);

        donateButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            JFrame donateFrame = new JFrame("Donation Frame");
            donateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            donateFrame.setSize(800, 400);
            donateFrame.setLocationRelativeTo(null);
            donateFrame.setLayout(null);
            donateFrame.setFont(componentFont);
            donateFrame.getContentPane().setBackground(Color.decode("#F7F4E6"));
            donateFrame.setVisible(true);
            donateFrame.setResizable(false);

            donateFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    mainFrame.setVisible(true);
                }
            });

            ImageIcon donationFrameIcon = new ImageIcon("src/Assets/donate frame head.png");
            JLabel donationFrameHeadLabel = new JLabel(donationFrameIcon);
            donationFrameHeadLabel.setBounds(0, 0, 800, 100);
            donateFrame.add(donationFrameHeadLabel);

            JLabel donateChoice = new JLabel("Type of donation :");
            donateChoice.setFont(componentFont);
            donateChoice.setBounds(230, 157, 150, 20);
            donateFrame.add(donateChoice);

            String[] donationTypes = {"Select", "Plant", "Money"};
            JComboBox<String> donationType = new JComboBox<>(donationTypes);
            donationType.setBounds(400, 150, 150, 35);
            donationType.setRenderer(new CenteredComboBoxRenderer());
            donationType.setFont(componentFont);
            donateFrame.add(donationType);

            donationType.addItemListener(itemEvent -> {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    String selectedType = (String) itemEvent.getItem();
                    donateFrame.getContentPane().removeAll();
                    donateFrame.add(donationFrameHeadLabel);
                    donateFrame.add(donateChoice);
                    donateFrame.add(donationType);
                    if ("Plant".equals(selectedType)) {
                        plantComponents(donateFrame, mainFrame);
                    } else if ("Money".equals(selectedType)) {
                        moneyComponents(donateFrame, mainFrame);
                    }
                    donateFrame.revalidate();
                    donateFrame.repaint();
                }
            });
        });

        orderButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            JFrame orderFrame = new JFrame("Order Frame");
            orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            orderFrame.setSize(800, 500);
            orderFrame.setLocationRelativeTo(null);
            orderFrame.setLayout(null);
            orderFrame.setFont(componentFont);
            orderFrame.getContentPane().setBackground(Color.decode("#F7F4E6"));
            orderFrame.setVisible(true);
            orderFrame.setResizable(false);

            orderFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    mainFrame.setVisible(true);
                }
            });

            orderMethod(orderFrame, mainFrame);
        });


        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 20, 100, 30);
        logoutButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(logoutButton);

        logoutButton.addActionListener(e -> new Thread(() -> {
            SessionUtil.setLoggedInUserId(0); // Clear the logged-in user ID
            SwingUtilities.invokeLater(() -> {
                new LoginPage(); // Redirect to login page
                mainFrame.dispose(); // Close the home page
            });
        }).start());


        if (SessionUtil.isAdmin()) {
            JButton adminButton = new JButton("Admin Page");
            adminButton.setBounds(20, 20, 150, 30);
            adminButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            panel.add(adminButton);

            adminButton.addActionListener(e -> {
                new AdminPage();
                mainFrame.setVisible(false);
            });
        }

    }

    private void orderMethod(JFrame orderFrame, JFrame mainFrame) {
        Font componentFont = new Font("Times New Roman", Font.PLAIN, 20);

        ImageIcon orderFrameIcon = new ImageIcon("src/Assets/order frame head.png");
        JLabel orderFrameHeadLabel = new JLabel(orderFrameIcon);
        orderFrameHeadLabel.setBounds(0, 0, 800, 100);
        orderFrame.add(orderFrameHeadLabel);

        JLabel plantTypeLabel = new JLabel("Plant type :");
        plantTypeLabel.setFont(componentFont);
        plantTypeLabel.setBounds(270, 152, 150, 20);
        orderFrame.add(plantTypeLabel);

        String[] plantTypes = getPlantTypesWithAvailability();
        JComboBox<String> plantType = new JComboBox<>(plantTypes);
        plantType.setBounds(380, 145, 150, 35);
        plantType.setRenderer(new CenteredComboBoxRenderer());
        plantType.setFont(componentFont);
        orderFrame.add(plantType);

        JLabel quantityLabel = new JLabel("Quantity :");
        quantityLabel.setFont(componentFont);
        quantityLabel.setBounds(280, 189, 150, 20);
        orderFrame.add(quantityLabel);

        JComboBox<Integer> quantityComboBox = new JComboBox<>();
        quantityComboBox.setBounds(380, 185, 150, 35);
        quantityComboBox.setRenderer(new CenteredComboBoxRenderer());
        quantityComboBox.setFont(componentFont);
        orderFrame.add(quantityComboBox);

        plantType.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                String selectedPlant = (String) itemEvent.getItem();
                updateQuantityComboBox(selectedPlant, quantityComboBox);
            }
        });

        JLabel addressLabel = new JLabel("Address :");
        addressLabel.setFont(componentFont);
        addressLabel.setBounds(284, 234, 150, 20);
        orderFrame.add(addressLabel);

        JTextArea addressArea = new JTextArea();
        addressArea.setBounds(380, 230, 150, 80);
        addressArea.setFont(componentFont);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        orderFrame.add(addressArea);

        JLabel paymentTypeLabel = new JLabel("Payment type :");
        paymentTypeLabel.setFont(componentFont);
        paymentTypeLabel.setBounds(240, 324, 150, 20);
        orderFrame.add(paymentTypeLabel);

        String[] paymentTypes = {"Select", "Cash", "Card", "UPI"};
        JComboBox<String> paymentType = new JComboBox<>(paymentTypes);
        paymentType.setBounds(380, 320, 150, 35);
        paymentType.setRenderer(new CenteredComboBoxRenderer());
        paymentType.setFont(componentFont);
        orderFrame.add(paymentType);

        JButton orderPlantNowButton = new JButton("Order plant");
        orderPlantNowButton.setBounds(227, 365, 325, 35);
        orderPlantNowButton.setFont(componentFont);
        orderFrame.add(orderPlantNowButton);

        orderPlantNowButton.addActionListener(e -> {
            String selectedPlant = (String) plantType.getSelectedItem();
            String address = addressArea.getText();
            String payment = (String) paymentType.getSelectedItem();
            Integer quantity = (Integer) quantityComboBox.getSelectedItem();
            if (selectedPlant != null && !selectedPlant.equals("Select") && !address.isEmpty() && !"Select".equals(payment) && quantity != null) {
                storeOrder(SessionUtil.getLoggedInUserId(), selectedPlant, quantity, address, payment);
                JOptionPane.showMessageDialog(orderFrame, "Thanks for ordering " + quantity + " " + selectedPlant + " plant(s)", "MESSAGE", JOptionPane.PLAIN_MESSAGE);
                orderFrame.dispose();
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(orderFrame, "Please fill all the fields correctly.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateQuantityComboBox(String plantType, JComboBox<Integer> quantityComboBox) {
        Connection connection = MySQL.getConnection();
        String query = "SELECT " + plantType.toLowerCase() + " FROM storage WHERE id = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int availableQuantity = resultSet.getInt(plantType.toLowerCase());
                quantityComboBox.removeAllItems();
                for (int i = 1; i <= availableQuantity; i++) {
                    quantityComboBox.addItem(i);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating quantity combo box for plant type: " + plantType, e);
        } finally {
            MySQL.closeConnection();
        }
    }

    private void plantComponents(JFrame donateFrame, JFrame mainFrame) {
        Font componentFont = new Font("Times New Roman", Font.PLAIN, 20);

        JLabel plantTypeLabel = new JLabel("Plant type :");
        plantTypeLabel.setFont(componentFont);
        plantTypeLabel.setBounds(287, 194, 150, 20);
        donateFrame.add(plantTypeLabel);

        String[] plantTypes = {"Select", "Hibiscus", "Mango", "Neem", "Banyan"};
        JComboBox<String> plantType = new JComboBox<>(plantTypes);
        plantType.setBounds(400, 190, 150, 35);
        plantType.setRenderer(new CenteredComboBoxRenderer());
        plantType.setFont(componentFont);
        donateFrame.add(plantType);

        JLabel numberOfTreesLabel = new JLabel("Number of trees :");
        numberOfTreesLabel.setFont(componentFont);
        numberOfTreesLabel.setBounds(237, 234, 150, 20);
        donateFrame.add(numberOfTreesLabel);

        Integer[] treeNumbers = new Integer[100];
        for (int i = 0; i < 100; i++) {
            treeNumbers[i] = i + 1;
        }
        JComboBox<Integer> numberOfTrees = new JComboBox<>(treeNumbers);
        numberOfTrees.setBounds(400, 230, 150, 35);
        numberOfTrees.setRenderer(new CenteredComboBoxRenderer());
        numberOfTrees.setFont(componentFont);
        numberOfTrees.setSelectedIndex(0);
        donateFrame.add(numberOfTrees);

        JButton donatePlantNowButton = new JButton("Donate plant");
        donatePlantNowButton.setBounds(227, 270, 325, 35);
        donatePlantNowButton.setFont(componentFont);
        donateFrame.add(donatePlantNowButton);

        donatePlantNowButton.addActionListener(e -> {
            String selectedPlant = (String) plantType.getSelectedItem();
            if ("Select".equals(selectedPlant)) {
                JOptionPane.showMessageDialog(donateFrame, "Please select a plant type.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int quantity = (numberOfTrees.getSelectedItem() != null) ? (Integer) numberOfTrees.getSelectedItem() : 0;
                assert selectedPlant != null;
                storeDonation(SessionUtil.getLoggedInUserId(), selectedPlant, selectedPlant + " (" + quantity + ")", quantity);
                JOptionPane.showMessageDialog(donateFrame, "Thanks for donating " + quantity + " " + selectedPlant + " plant(s)", "MESSAGE", JOptionPane.PLAIN_MESSAGE);
                donateFrame.dispose();
                mainFrame.setVisible(true);
            }
        });
    }

    private void moneyComponents(JFrame donateFrame, JFrame mainFrame) {
        Font componentFont = new Font("Times New Roman", Font.PLAIN, 20);

        JLabel amountLabel = new JLabel("Amount :");
        amountLabel.setFont(componentFont);
        amountLabel.setBounds(301, 214, 150, 20);
        donateFrame.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(400, 210, 150, 35);
        amountField.setMargin(new Insets(0, 5, 0, 5));
        amountField.setFont(componentFont);
        donateFrame.add(amountField);

        JButton donateMoneyNowButton = new JButton("Donate money");
        donateMoneyNowButton.setBounds(227, 270, 325, 35);
        donateMoneyNowButton.setFont(componentFont);
        donateFrame.add(donateMoneyNowButton);

        donateMoneyNowButton.addActionListener(e -> {
            String amount = amountField.getText();
            storeDonation(SessionUtil.getLoggedInUserId(), "Money", amount, 0);
            JOptionPane.showMessageDialog(donateFrame, "Thanks for donating Rs." + amount, "MESSAGE", JOptionPane.PLAIN_MESSAGE);
            donateFrame.dispose();
            mainFrame.setVisible(true);
        });
    }

    private void storeDonation(int userId, String donationType, String donationDetail, int quantity) {
        Connection connection = MySQL.getConnection();
        String query = "INSERT INTO donations (user_id, donation_type, donation_detail) VALUES (?, ?, ?)";
        String updateStorageQuery;

        if (donationType.equalsIgnoreCase("Money")) {
            updateStorageQuery = "UPDATE storage SET money_collected = money_collected + ? WHERE id = 1";
        } else {
            updateStorageQuery = "UPDATE storage SET " + donationType.toLowerCase() + " = " + donationType.toLowerCase() + " + ? WHERE id = 1";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             PreparedStatement updateStorageStmt = connection.prepareStatement(updateStorageQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, donationType);
            preparedStatement.setString(3, donationDetail);
            preparedStatement.executeUpdate();

            if (donationType.equalsIgnoreCase("Money")) {
                updateStorageStmt.setInt(1, Integer.parseInt(donationDetail));
            } else {
                updateStorageStmt.setInt(1, quantity);
            }
            updateStorageStmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error storing donation.", e);
        } finally {
            MySQL.closeConnection();
        }
    }

    private void storeOrder(int userId, String plantType, int quantity, String address, String paymentType) {
        int plantPrice = switch (plantType.toLowerCase()) {
            case "neem" -> 50;
            case "mango" -> 80;
            case "hibiscus" -> 100;
            case "banyan" -> 150;
            default -> 0; // Default price if plant type is not recognized
        };
        Connection connection = MySQL.getConnection();
        String query = "INSERT INTO orders (user_id, hibiscus, mango, neem, banyan, price, address, payment_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateStorageQuery = "UPDATE storage SET " + plantType.toLowerCase() + " = " + plantType.toLowerCase() + " - ?, money_collected = money_collected + ? WHERE id = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement updateStorageStmt = connection.prepareStatement(updateStorageQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, plantType.equalsIgnoreCase("Hibiscus") ? quantity : 0);
            preparedStatement.setInt(3, plantType.equalsIgnoreCase("Mango") ? quantity : 0);
            preparedStatement.setInt(4, plantType.equalsIgnoreCase("Neem") ? quantity : 0);
            preparedStatement.setInt(5, plantType.equalsIgnoreCase("Banyan") ? quantity : 0);
            preparedStatement.setInt(6, plantPrice * quantity);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, paymentType);
            preparedStatement.executeUpdate();

            updateStorageStmt.setInt(1, quantity);
            updateStorageStmt.setInt(2, plantPrice * quantity);
            updateStorageStmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error storing donation.", e);
        } finally {
            MySQL.closeConnection();
        }
    }

    private String[] getPlantTypesWithAvailability() {
        Connection connection = MySQL.getConnection();
        String query = "SELECT hibiscus, mango, neem, banyan FROM storage WHERE id = 1";
        java.util.List<String> plantTypes = new java.util.ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                if (resultSet.getInt("hibiscus") > 0) plantTypes.add("Hibiscus");
                if (resultSet.getInt("mango") > 0) plantTypes.add("Mango");
                if (resultSet.getInt("neem") > 0) plantTypes.add("Neem");
                if (resultSet.getInt("banyan") > 0) plantTypes.add("Banyan");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting availability of plant types.", e);
        } finally {
            MySQL.closeConnection();
        }

        return plantTypes.toArray(new String[0]);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}