package com.smartmeter.dao.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.Bill;

import java.sql.*;

public class BillDAOImpl implements BillDAO {

    @Override
    public int createBill(int userId, int meterReadingId,
            double consumption, double amount) {

        String sql = """
            INSERT INTO bills (user_id, meter_reading_id, consumption, amount, status)
            VALUES (?, ?, ?, ?, 'UNPAID')
        """;
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps
                    = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public boolean markAsPaid(int billId) {
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps
                    = c.prepareStatement("UPDATE bills SET status='PAID' WHERE id=?")) {

                ps.setInt(1, billId);
                return ps.executeUpdate() > 0;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getBillAmount(int billId) {
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps
                    = c.prepareStatement("SELECT amount FROM bills WHERE id=?")) {

                ps.setInt(1, billId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getDouble(1);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Bill getUnpaidBillByUser(int userId) {

        String sql = """
            SELECT * FROM bills
            WHERE user_id=? AND status='UNPAID'
            ORDER BY id DESC LIMIT 1
        """;
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return new Bill(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getDouble("consumption"),
                            rs.getDouble("amount"),
                            rs.getString("status")
                    );
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
