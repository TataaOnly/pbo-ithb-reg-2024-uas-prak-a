package Control;

import Model.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseController {

    private static final DatabaseManager db = DatabaseManager.getInstance();

    public Customer authenticateUser(String phone, String password) {
        try {
            db.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                String hashedPassword = password;
                pstmt.setString(1, phone);
                pstmt.setString(2, hashedPassword);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new Customer(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return null;
    }

    public boolean isPhoneExists(String phone) {
        try {
            db.connect();
            String query = "SELECT phone FROM customer WHERE phone = ?";
            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                pstmt.setString(1, phone);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            System.err.println("Phone check error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            db.disconnect();
        }
    }

    public int getLoggedUser(String phone) {
        int idCustomer = -1; // Default value for invalid customer ID
        try {
            db.connect();
            String query = "SELECT id FROM customer WHERE phone = ?";
            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                pstmt.setString(1, phone);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idCustomer = rs.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customer ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
        return idCustomer;
    }
    

    public boolean AddShipment(int idCustomer, String totalCost, String packageType, String packageWeight, String receiptName, String receiptAddress, String receiptPhone) {
        // Debug print
        System.out.println("Attempting to register: packageType: " + packageType + ", packageWeight: " + packageWeight);


        try {
            db.connect();
            // Debug print
            System.out.println("Database connected");

            String query = "INSERT INTO transaction (customer_id, package_type, package_weight, total_cost, created_at, receipt_name, receipt_address, receipt_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                pstmt.setInt(1, idCustomer);
                pstmt.setString(2, packageType);
                pstmt.setString(3, packageWeight);
                pstmt.setString(4, totalCost);
                pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                pstmt.setString(6, receiptName);
                pstmt.setString(7, receiptAddress);
                pstmt.setString(8, receiptPhone);

                System.out.println("id:" +idCustomer);
                // Debug print
                System.out.println("Executing query: " + pstmt.toString());

                int rowsAffected = pstmt.executeUpdate();

                // Debug print
                System.out.println("Rows affected: " + rowsAffected);

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            // Enhanced error printing
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            db.disconnect();
        }
    }

    public boolean registerCustomer(String name, String password, String address, String phone) {
        // Debug print
        System.out.println("Attempting to register: " + name + ", phone: " + phone);

        if (isPhoneExists(phone)) {
            System.out.println("Phone already exists: " + phone);
            return false;
        }

        try {
            db.connect();
            // Debug print
            System.out.println("Database connected");

            String query = "INSERT INTO customer (name, password, address, phone) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.setString(3, address);
                pstmt.setString(4, phone);

                // Debug print
                System.out.println("Executing query: " + pstmt.toString());

                int rowsAffected = pstmt.executeUpdate();

                // Debug print
                System.out.println("Rows affected: " + rowsAffected);

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            // Enhanced error printing
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            db.disconnect();
        }
    }

    public boolean addTransaction(String transactionId, String status, String currentPosition, String uploadedBy, File evidence) {
        String insertSQL = "INSERT INTO transactions (transaction_id, status, current_position, uploaded_by, evidence) VALUES (?, ?, ?, ?, ?)";

        try {
            db.connect();
            try (PreparedStatement pstmt = db.con.prepareStatement(insertSQL)) {

                pstmt.setString(1, transactionId);
                pstmt.setString(2, status);
                pstmt.setString(3, currentPosition);
                pstmt.setString(4, uploadedBy);

                // Convert the file to a byte array for storage in the database
                if (evidence != null) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(evidence);
                        pstmt.setBinaryStream(5, fis, (int) evidence.length());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        if (fis != null) {
                            try {
                                fis.close();  // Explicitly close the stream
                            } catch (IOException e) {
                                e.printStackTrace();  // Handle the IOException when closing
                            }
                        }
                    }
                } else {
                    pstmt.setNull(5, Types.BLOB);
                }

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            db.disconnect();
        }
    }
}
