package com.smartmeter.patterns.observer;

import com.smartmeter.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogObserver implements Observer {

    @Override
    public void update(String message, Integer userId) {

        String sql = """
            INSERT INTO logs (user_id, event_type, level, message)
            VALUES (?, ?, ?, ?)
        """;

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setObject(1, userId);
            stmt.setString(2, "USER");
            stmt.setString(3, "INFO");
            stmt.setString(4, message);

            stmt.executeUpdate();
            System.out.println("[LOG SAVED] " + message);

        } catch (Exception e) {
            System.err.println("[LOG FAILED] " + e.getMessage());
        }
    }
}
