import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminPage extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AdminPage.class.getName());

    public AdminPage() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full screen
        setLocationRelativeTo(null);

        // Set custom look and feel
        setCustomLookAndFeel();

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        loadData(tabbedPane);

        // Add button to redirect to HomePage
        JButton homeButton = new JButton("Go to Home Page");
        homeButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        homeButton.addActionListener(e -> {
            new HomePage();
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(homeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add window listener to redirect to HomePage on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new HomePage();
            }
        });

        setVisible(true);
    }

    private void setCustomLookAndFeel() {
        UIManager.put("TabbedPane.selected", Color.LIGHT_GRAY);
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("Table.font", new Font("SansSerif", Font.PLAIN, 18));
        UIManager.put("Table.rowHeight", 30);
        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.gridColor", Color.LIGHT_GRAY);
        UIManager.put("TableHeader.font", new Font("SansSerif", Font.BOLD, 18));
        UIManager.put("TableHeader.background", Color.GRAY);
        UIManager.put("TableHeader.foreground", Color.BLACK);
    }

    private void loadData(JTabbedPane tabbedPane) {
        Connection connection = MySQL.getConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (!"sys_config".equalsIgnoreCase(tableName) && tabbedPane.indexOfTab(tableName) == -1) {
                    JTable table = new JTable();
                    table.setFont(new Font("SansSerif", Font.PLAIN, 18));
                    table.setRowHeight(30);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    table.setDefaultRenderer(Object.class, new WordWrapCellRenderer());

                    DefaultTableModel model = getTableData(connection, tableName);
                    table.setModel(model);
                    setColumnWidths(table);

                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    tabbedPane.addTab(tableName, scrollPane);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading data.", e);
        } finally {
            MySQL.closeConnection();
        }
    }

    private DefaultTableModel getTableData(Connection connection, String tableName) throws SQLException {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String query = "SELECT * FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                model.addRow(row);
            }
        }
        return model;
    }

    private void setColumnWidths(JTable table) {
        int[] columnWidths = {166, 166, 166, 166, 166}; // Example widths
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i % columnWidths.length]);
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to set system look and feel. Using default.", e);
        }

        SwingUtilities.invokeLater(AdminPage::new);
    }
}