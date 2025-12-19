package com.smartmeter.dao.impl;

import com.smartmeter.dao.ReportDAO;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public boolean addReport(Report report) {
        String sql = "INSERT INTO reports (user_id, title, message) VALUES (?, ?, ?)";

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, report.getUserId());
            ps.setString(2, report.getTitle());
            ps.setString(3, report.getMessage());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Report> getAllReports() {

        List<Report> list = new ArrayList<>();

        String sql = "SELECT * FROM reports ORDER BY created_at DESC";

        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Report(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
