package View;

import Control.DatabaseManager;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShippingDetails extends JFrame {
    private JTable shipmentTable;
    private DefaultTableModel tableModel;
    private DatabaseManager db;

    public ShippingDetails() {
        // Frame setup
        setTitle("Shipment Details");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 51, 153),
                    getWidth(), getHeight(), new Color(0, 105, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Shipment Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table
        String[] columns = {"Status", "Evidence", "Date", "Updated By"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        shipmentTable = new JTable(tableModel);
        shipmentTable.setFillsViewportHeight(true);
        shipmentTable.setBackground(Color.WHITE);
        shipmentTable.setForeground(Color.BLACK);
        shipmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        shipmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(shipmentTable);
        scrollPane.setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 51, 153));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> dispose()); // Close current frame
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        loadShipmentDetails();
    }

    private void loadShipmentDetails() {
        tableModel.setRowCount(0);

        try {
            db = DatabaseManager.getInstance(); // Get database instance
            db.connect(); // Connect to database

            String query = "SELECT status, evidence, date, updated_by FROM shipment_details ORDER BY id DESC";
            PreparedStatement stmt = db.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("status"),
                    rs.getString("evidence"),
                    rs.getDate("date"),
                    rs.getString("updated_by")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            db.disconnect(); // Disconnect from database
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading shipment details: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
