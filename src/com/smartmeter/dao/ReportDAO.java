package com.smartmeter.dao;

import com.smartmeter.model.Report;
import java.util.List;

public interface ReportDAO {

    boolean addReport(Report report);

    List<Report> getAllReports();
}
