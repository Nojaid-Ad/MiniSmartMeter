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
            INSERT INTO meter_readings (user_id, reading, billed)
            VALUES (?, ?, false)
        """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setDouble(2, reading);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public MeterReading getLastReading(int userId) {

        String sql = """
        SELECT * FROM meter_readings
        WHERE user_id = ?
        ORDER BY created_at DESC
        LIMIT 1
    """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MeterReading(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("reading"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean hasUnbilledConsumption(int userId) {
        return false;
    }

    @Override
    public MeterReading getPreviousReading(int userId) {

        String sql = """
        SELECT * FROM meter_readings
        WHERE user_id = ?
        ORDER BY id DESC
        LIMIT 1 OFFSET 1
    """;
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new MeterReading(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getDouble("reading"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
