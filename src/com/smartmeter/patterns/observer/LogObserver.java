package com.smartmeter.patterns.observer;

import com.smartmeter.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogObserver implements Observer {

    @Override
    public void update(String message) {

        String sql = "INSERT INTO logs (message) VALUES (?)";

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, message);
            stmt.executeUpdate();

            System.out.println("[LOG SAVED] " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
