package com.smartmeter.service;

import com.smartmeter.model.Report;
import java.util.List;

public interface ReportService {

    boolean submitReport(int userId, String title, String message);

    List<Report> getAllReports();
}
