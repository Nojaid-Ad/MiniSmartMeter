package com.smartmeter.dao.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillDAOImpl implements BillDAO {

    @Override
    public int createBill(int userId, int meterReadingId, double consumption, double amount) {

        String sql = """
            INSERT INTO bills (user_id, meter_reading_id, consumption, amount, status)
            VALUES (?, ?, ?, ?, 'unpaid')
        """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps =
                    c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userId);
                ps.setInt(2, meterReadingId);
                ps.setDouble(3, consumption);
                ps.setDouble(4, amount);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean markAsPaid(int billId) {

        String sql = "UPDATE bills SET status = 'paid' WHERE id = ?";

        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, billId);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public double getBillAmount(int billId) {

        String sql = "SELECT amount FROM bills WHERE id = ?";

        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, billId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getDouble("amount");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
