package View;

import java.awt.*;
import javax.swing.*;

public class MainPanel extends JFrame {

    // Declare buttonPanel as an instance variable
    private JPanel buttonPanel;

    public MainPanel() {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame configuration
        setTitle("PBO - UAS");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("UAS P.PBO", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Initialize buttonPanel
        buttonPanel = new JPanel(new GridLayout(4, 1, 10, 20));
        buttonPanel.setOpaque(false);

        // Create custom styled buttons
        JButton loginButton = createStyledButton("Login", true);
        JButton regisButton = createStyledButton("Registr", true);
        JButton TransactionButton = createStyledButton("Transaction", true);

        buttonPanel.add(loginButton);
        buttonPanel.add(regisButton);
        buttonPanel.add(TransactionButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Logout Button
        // JButton logoutButton = createLogoutButton();
        // mainPanel.add(logoutButton, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
        regisButton.addActionListener(e -> new RegisterFrame().setVisible(true));
        // TransactionButton.addActionListener(e -> new ManageUserFrame());

        // Add main panel to frame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);

        // Add transactionHistoryButton after buttonPanel is initialized
        addTransactionHistoryButton();
    }

    // Method to add the transaction history button
    private void addTransactionHistoryButton() {
        JButton transactionHistoryButton = createStyledButton("Transaction History", true);
        buttonPanel.add(transactionHistoryButton);

        // Add its action listener
        // transactionHistoryButton.addActionListener(e -> new TransactionHistoryFrame());

        // Refresh the UI
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    // Custom styled button method
    private JButton createStyledButton(String text, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));

        if (isPrimary) {
            button.setBackground(new Color(0, 51, 153));
            button.setForeground(new Color(248, 209, 21));
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        } else {
            button.setBackground(new Color(0, 51, 153));
            button.setForeground(new Color(4, 52, 140));
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }

        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
