package com.smartmeter.service.impl;

import com.smartmeter.dao.ReportDAO;
import com.smartmeter.dao.impl.ReportDAOImpl;
import com.smartmeter.model.Report;
import com.smartmeter.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    public boolean submitReport(int userId, String title, String message) {
        return reportDAO.addReport(new Report(userId, title, message));
    }

    @Override
    public List<Report> getAllReports() {
        return reportDAO.getAllReports();
    }
}
