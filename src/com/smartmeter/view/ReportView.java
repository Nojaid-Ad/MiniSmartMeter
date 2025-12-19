package com.smartmeter.view;

import com.smartmeter.model.Report;
import java.util.List;

public class ReportView {

    public void showReports(List<Report> reports) {
        if (reports.isEmpty()) {
            System.out.println("No reports found.");
            return;
        }

        System.out.println(String.format(
                "%-20s | %-30s | %-20s",
                "Title", "Message", "Date"
        ));
        System.out.println("--------------------------------------------------------------------");

        for (Report r : reports) {
            System.out.println(String.format(
                    "%-20s | %-30s | %-20s",
                    r.getTitle(),
                    r.getMessage(),
                    r.getCreatedAt()
            ));
        }
    }
}
