package View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

enum Status {
    PENDING, SHIPPED, DELIVERED, CANCELLED
}

public class TransactionDetails extends JFrame {
    public TransactionDetails() {
        // Frame setup
        JFrame frame = new JFrame("Detail Shipment");
        frame.setSize(450, 600); // Adjusted height for better spacing
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with IKEA-style gradient background
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 51, 153), // Dark blue
                        getWidth(), getHeight(), new Color(0, 105, 255) // Lighter blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        menuPanel.setLayout(null);

        // Title labels
        JLabel title1 = new JLabel("UAS");
        title1.setBounds(0, 0, 450, 50);
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font("Sans Serif", Font.BOLD, 24));
        title1.setForeground(Color.WHITE);
        menuPanel.add(title1);

        JLabel title2 = new JLabel("Making a Detail Shipment");
        title2.setBounds(0, 40, 450, 50);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setFont(new Font("SansSerif", Font.BOLD, 24));
        title2.setForeground(Color.WHITE);
        menuPanel.add(title2);

        int naikkinField = -35;
        int geserinField = -10;

        // Transaction ID
        JLabel transactionLabel = new JLabel("Transaction ID:");
        transactionLabel.setBounds(geserinField + 80, naikkinField + 150, 120, 25);
        transactionLabel.setForeground(Color.WHITE);
        menuPanel.add(transactionLabel);

        JTextField transactionField = new JTextField();
        transactionField.setBounds(geserinField + 200, naikkinField + 150, 200, 25);
        menuPanel.add(transactionField);

        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(geserinField + 80, naikkinField + 200, 120, 25);
        statusLabel.setForeground(Color.WHITE);
        menuPanel.add(statusLabel);

        JComboBox<Status> statusCB = new JComboBox<>(Status.values());
        statusCB.setBounds(geserinField + 200, naikkinField + 200, 200, 25);
        menuPanel.add(statusCB);

        // Current Position
        JLabel currentPositionLabel = new JLabel("Current Position:");
        currentPositionLabel.setBounds(geserinField + 80, naikkinField + 250, 120, 25);
        currentPositionLabel.setForeground(Color.WHITE);
        menuPanel.add(currentPositionLabel);

        JTextField currentPositionField = new JTextField();
        currentPositionField.setBounds(geserinField + 200, naikkinField + 250, 200, 25);
        menuPanel.add(currentPositionField);

        // Evidence upload
        JLabel evidenceLabel = new JLabel("Evidence:");
        evidenceLabel.setBounds(geserinField + 80, naikkinField + 300, 120, 25);
        evidenceLabel.setForeground(Color.WHITE);
        menuPanel.add(evidenceLabel);

        JButton uploadButton = createStyledButton("Upload Photo");
        uploadButton.setBounds(geserinField + 200, naikkinField + 300, 200, 25);
        menuPanel.add(uploadButton);

        JLabel uploadedImageLabel = new JLabel("No image selected", JLabel.CENTER);
        uploadedImageLabel.setBounds(geserinField + 200, naikkinField + 350, 200, 100);
        uploadedImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        menuPanel.add(uploadedImageLabel);

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an Image");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "gif", "jpeg"));

            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage img = ImageIO.read(selectedFile);
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 100, Image.SCALE_SMOOTH));
                    uploadedImageLabel.setIcon(icon);
                    uploadedImageLabel.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Uploaded By
        JLabel uploadByLabel = new JLabel("Uploaded By:");
        uploadByLabel.setBounds(geserinField + 80, naikkinField + 460, 120, 25);
        uploadByLabel.setForeground(Color.WHITE);
        menuPanel.add(uploadByLabel);

        JTextField uploadByField = new JTextField();
        uploadByField.setBounds(geserinField + 200, naikkinField + 460, 200, 25);
        menuPanel.add(uploadByField);

        // Add and Back buttons
        JButton addButton = createStyledButton("Add");
        addButton.setBounds(250, 520, 150, 50);
        menuPanel.add(addButton);

        JButton backButton = createStyledButton("Back");
        backButton.setBounds(50, 520, 150, 50);
        menuPanel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();
            new AfterLogin();
        });

        addButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Transaction Details Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Final frame setup
        frame.add(menuPanel);
        frame.setVisible(true);
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(248, 209, 21)); // IKEA yellow
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TransactionDetails::new);
    }
}
