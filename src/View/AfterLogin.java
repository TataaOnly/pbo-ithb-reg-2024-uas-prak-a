package View;

import java.awt.*;
import javax.swing.*;

public class AfterLogin extends JFrame {

    private JPanel buttonPanel;

    public AfterLogin() {
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
        JButton addShipmentButton = createStyledButton("Add Shipment", true);
        JButton regisButton = createStyledButton("Transaction Details", true);
        JButton transactionButton = createStyledButton("Transaction", true);

        // Add buttons to the panel
        buttonPanel.add(addShipmentButton);
        buttonPanel.add(regisButton);
        buttonPanel.add(transactionButton);

        // Add buttonPanel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);
        setVisible(true);

        addShipmentButton.addActionListener(e -> new AddShipment().setVisible(true));
        regisButton.addActionListener(e -> new TransactionDetails().setVisible(true));
        transactionButton.addActionListener(e -> new TransactionHistory().setVisible(true));
    }

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
