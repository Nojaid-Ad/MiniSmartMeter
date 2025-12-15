package com.smartmeter.dao.impl;

import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.MeterReading;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MeterReadingDAOImpl implements MeterReadingDAO {

    @Override
    public boolean addReading(int userId, double reading) {

        String sql = """
            INSERT INTO meter_readings (user_id, reading)
            VALUES (?, ?)
        """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = c.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setDouble(2, reading);
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public MeterReading getLastReading(int userId) {

        String sql = """
            SELECT * FROM meter_readings
            WHERE user_id = ?
            ORDER BY reading_date DESC
            LIMIT 1
        """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement stmt = c.prepareStatement(sql)) {

                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new MeterReading(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getDouble("reading"),
                            rs.getTimestamp("reading_date").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
