package View;

import Control.DatabaseController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame {
    private DatabaseController dbController;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField addressField;
    private JTextField phoneField;

    public RegisterFrame() {
        dbController = new DatabaseController();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("IKEA Marketplace - Register");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);
        JLabel logoLabel = new JLabel("Register");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(new Color(248, 209, 21));
        logoPanel.add(logoLabel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // Adding form fields
        addFormField(formPanel, "Name:", nameField = new JTextField(), gbc, 0);
        addFormField(formPanel, "Password:", passwordField = new JPasswordField(), gbc, 2);
        addFormField(formPanel, "Confirm Password:", confirmPasswordField = new JPasswordField(), gbc, 4);
        addFormField(formPanel, "Address:", addressField = new JTextField(), gbc, 6);
        addFormField(formPanel, "Phone (08XXX):", phoneField = new JTextField(), gbc, 8);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        JButton registerButton = createStyledButton("Register", true);
        JButton backButton = createStyledButton("Back to Login", false);

        registerButton.addActionListener(e -> handleRegistration());
        backButton.addActionListener(e -> {
            dispose();
            new MainPanel();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(logoPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void handleRegistration() {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String address = addressField.getText();
        String phone = phoneField.getText();
    
        // Validation
        if (name.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            showMessage("Error", "Semua field harus diisi!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!password.equals(confirmPassword)) {
            showMessage("Error", "Password tidak cocok!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // if (!phone.startsWith("08")) {
        //     showMessage("Error", "Nomor telepon harus diawali dengan 08!", JOptionPane.ERROR_MESSAGE);
        //     return;
        // }
    
        if (dbController.registerCustomer(name, password, address, phone)) {
            showMessage("Success", "Registrasi berhasil! Silakan login.", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame();
        } else {
            showMessage("Error", "Nomor telepon sudah digunakan atau terjadi kesalahan.", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
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

    private void addFormField(JPanel panel, String labelText, JComponent field, 
                            GridBagConstraints gbc, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        gbc.gridy = gridy;
        panel.add(label, gbc);

        gbc.gridy = gridy + 1;
        field.setPreferredSize(new Dimension(300, 40));
        panel.add(field, gbc);
    }
}