package com.smartmeter.dao.impl;

import com.smartmeter.dao.PaymentDAO;
import com.smartmeter.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean savePayment(int userId, int billId, double amount, String method) {

        String sql = """
            INSERT INTO payments (user_id, bill_id, amount, method)
            VALUES (?, ?, ?, ?)
        """;
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = c.prepareStatement(sql)) {

                stmt.setInt(1, userId);
                stmt.setInt(2, billId);
                stmt.setDouble(3, amount);
                stmt.setString(4, method);

                return stmt.executeUpdate() > 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
