package View;

import Control.DatabaseManager;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionHistory extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private DatabaseManager db;

    public TransactionHistory() {
        // Frame setup
        setTitle("Transaction History");
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
        JLabel titleLabel = new JLabel("Transaction History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table
        String[] columns = {"Transaction ID", "Package Type", "Package Weight", "Total Cost", "Created At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
        transactionTable.setBackground(Color.WHITE);
        transactionTable.setForeground(Color.BLACK);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBackground(Color.WHITE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(200, 200, 200));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.addActionListener(e -> loadTransactions());
        buttonPanel.add(refreshButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(200, 200, 200));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> {
            this.dispose();
            new AfterLogin().setVisible(true);
        });
        buttonPanel.add(backButton);

        JButton shippingButton = new JButton("Shipping Details");
        shippingButton.setFont(new Font("Arial", Font.BOLD, 14));
        shippingButton.setBackground(new Color(200, 200, 200));
        shippingButton.setForeground(Color.BLACK);
        shippingButton.addActionListener(e -> {
            this.dispose();
            new ShippingDetails().setVisible(true);
        });
        buttonPanel.add(shippingButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        loadTransactions();
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);

        try {
            db = DatabaseManager.getInstance(); // Get database instance
            db.connect(); // Connect to database

            String query = "SELECT id, package_type, package_weight, total_cost, created_at FROM transaction ORDER BY created_at DESC";
            PreparedStatement stmt = db.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("package_type"),
                    rs.getDouble("package_weight"),
                    String.format("$%.2f", rs.getDouble("total_cost")),
                    rs.getDate("created_at")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            db.disconnect(); // Disconnect from database
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading transactions: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}