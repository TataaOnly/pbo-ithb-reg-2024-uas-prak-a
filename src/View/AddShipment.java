package View;

import Control.DatabaseController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddShipment extends JFrame {

    private DatabaseController dbController;
    private JTextField nameField;
    private JTextField weightField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<String> comboBox;

    public AddShipment() {
        dbController = new DatabaseController();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Add Shipment");
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
        JLabel logoLabel = new JLabel("Add Shipment");
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
        addFormField(formPanel, "Address:", addressField = new JTextField(), gbc, 6);
        addFormField(formPanel, "Weight:", weightField = new JTextField(), gbc, 2);
        addFormField(formPanel, "Phone (08XXX):", phoneField = new JTextField(), gbc, 8);

        // Adding ComboBox
        gbc.gridwidth = 2;
        addFormField(formPanel, "Shipping Type:", comboBox = new JComboBox<>(), gbc, 10);
        comboBox.addItem("Reguler");
        comboBox.addItem("Express");
        comboBox.addItem("Same Day");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        JButton registerButton = createStyledButton("Register", true);
        JButton backButton = createStyledButton("Back to Login", false);

        registerButton.addActionListener(e -> handleRegistration());
        backButton.addActionListener(e -> {
            dispose();
            new AfterLogin();
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
        String weight = weightField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String packageType = (String) comboBox.getSelectedItem();

        // Retrieve customer ID using phone number
        int currentCustomerId = dbController.getLoggedUser(phone);

        // Validate customer ID
        if (currentCustomerId == -1) {
            showMessage("Error", "Phone number is not registered!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation
        if (name.isEmpty() || weight.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            showMessage("Error", "Semua field harus diisi!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalCost = 0;
        try {
            totalCost = Double.parseDouble(weight);
            if (totalCost > 0 && totalCost < 1) {
                totalCost = 1;
            }else{
                int tampung = (int) (totalCost);
                double decimal = totalCost - tampung;
                if (decimal >= 0.5) {
                    totalCost = tampung + 1;
                }else{
                    totalCost = tampung;
                }
            }
            
        } catch (Exception e) {
            showMessage("Error", "Weight must be a number!", JOptionPane.ERROR_MESSAGE);
            return;
        } // Placeholder, replace with actual calculation

        if (dbController.AddShipment(currentCustomerId, String.valueOf(totalCost), packageType, weight, name, address, phone)) {
            showMessage("Success", "Penambahan berhasil!", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AfterLogin();
        } else {
            showMessage("Error", "Terjadi kesalahan.", JOptionPane.ERROR_MESSAGE);
        }
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

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        gbc.gridy = gridy;
        panel.add(label, gbc);

        gbc.gridy = gridy + 1;
        field.setPreferredSize(new Dimension(300, 40));
        panel.add(field, gbc);
    }
}
