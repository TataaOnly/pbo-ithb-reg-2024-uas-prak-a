package View;

import Control.DatabaseController;
import Model.Customer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField phoneField;
    private JPasswordField passwordField;
    private DatabaseController dbController;

    public LoginFrame() {
        dbController = new DatabaseController();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("IKEA Marketplace - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 51, 153),
                        getWidth(), getHeight(), new Color(0, 105, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);

        // Add the image to the logo panel
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setIcon(new ImageIcon("lib/37.jpeg"));
        logoPanel.add(imageLabel, BorderLayout.NORTH);

        // Add title label
        JLabel logoLabel = new JLabel("Login", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(new Color(248, 209, 21));
        logoPanel.add(logoLabel, BorderLayout.SOUTH);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Phone:");
        usernameLabel.setForeground(Color.WHITE);
        loginPanel.add(usernameLabel, gbc);

        gbc.gridy = 1;
        phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(300, 40));
        loginPanel.add(phoneField, gbc);

        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        loginPanel.add(passwordLabel, gbc);

        gbc.gridy = 3;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        JButton loginButton = createStyledButton("Login", true);
        JButton backButton = createStyledButton("Back to Login", false);

        backButton.addActionListener(e -> {
            dispose();
            new MainPanel();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());
        
                Customer user = dbController.authenticateUser(phone, password);
                if (user != null) {
                    showCustomDialog("Login Berhasil",
                            "Selamat datang, " + user.getName() + "!",
                            JOptionPane.INFORMATION_MESSAGE);
        
                    dispose();  // Close LoginFrame
        
                    // Make sure to show AfterLogin frame after LoginFrame is disposed
                    AfterLogin afterLoginFrame = new AfterLogin();
                    afterLoginFrame.setVisible(true); // Show the AfterLogin frame
                } else {
                    showCustomDialog("Login Gagal",
                            "Nomor telepon atau password salah!\nSilakan coba lagi.",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));

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

    private void showCustomDialog(String title, String message, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(this, title);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
