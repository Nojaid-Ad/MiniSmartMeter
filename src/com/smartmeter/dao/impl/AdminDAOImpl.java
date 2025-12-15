package com.smartmeter.dao.impl;

import com.smartmeter.dao.AdminDAO;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.Admin;
import com.smartmeter.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public Admin login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addAdmin(String username, String password) {
        String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, password);
                return ps.executeUpdate() > 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id=?";
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt(1, userId);
                return ps.executeUpdate() > 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try (Statement st = c.createStatement()) {

                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getDouble("balance"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
