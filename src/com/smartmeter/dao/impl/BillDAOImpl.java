package com.smartmeter.dao.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.db.DBConnection;

import java.sql.*;

public class BillDAOImpl implements BillDAO {

    @Override
    public int createBill(int userId, double consumption, double amount) {

        String sql = """
            INSERT INTO bills (user_id, consumption, amount)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement stmt
                = DBConnection.getInstance()
                        .getConnection()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setInt(1, userId);
                    stmt.setDouble(2, consumption);
                    stmt.setDouble(3, amount);
                    stmt.executeUpdate();

                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getInt(1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -1;
    }

    @Override
    public boolean markAsPaid(int billId) {
        String sql = "UPDATE bills SET status='paid' WHERE id=?";
        try (PreparedStatement stmt
                = DBConnection.getInstance().getConnection().prepareStatement(sql)) {

            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
