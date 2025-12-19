package com.smartmeter.util;

import com.smartmeter.model.Bill;
import com.smartmeter.model.User;

import java.io.FileWriter;
import java.io.IOException;

public class BillPdfGenerator {

    public static void generate(Bill bill, User user) {

        String fileName = "bill_" + bill.getId() + ".pdf";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("%PDF-1.4\n");

            writer.write("1 0 obj\n");
            writer.write("<< /Type /Catalog /Pages 2 0 R >>\n");
            writer.write("endobj\n");

            writer.write("2 0 obj\n");
            writer.write("<< /Type /Pages /Kids [3 0 R] /Count 1 >>\n");
            writer.write("endobj\n");

            writer.write("3 0 obj\n");
            writer.write("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] ");
            writer.write("/Contents 4 0 R /Resources << >> >>\n");
            writer.write("endobj\n");

            String text
                    = "Smart Meter Bill\\n\\n"
                    + "User: " + user.getUsername() + "\\n"
                    + "Bill ID: " + bill.getId() + "\\n"
                    + "Consumption: " + bill.getConsumption() + " kWh\\n"
                    + "Amount: " + bill.getAmount() + " LYD\\n"
                    + "Status: " + (bill.isPaid() ? "PAID" : "UNPAID") + "\\n";

            writer.write("4 0 obj\n");
            writer.write("<< /Length " + text.length() + " >>\n");
            writer.write("stream\n");
            writer.write("BT\n");
            writer.write("/F1 12 Tf\n");
            writer.write("50 700 Td\n");
            writer.write("(" + text + ") Tj\n");
            writer.write("ET\n");
            writer.write("endstream\n");
            writer.write("endobj\n");

            writer.write("xref\n");
            writer.write("0 5\n");
            writer.write("0000000000 65535 f \n");
            writer.write("trailer\n");
            writer.write("<< /Size 5 /Root 1 0 R >>\n");
            writer.write("startxref\n");
            writer.write("%%EOF");

            System.out.println("PDF created successfully: " + fileName);

        } catch (IOException e) {
            System.out.println("Failed to create PDF");
        }
    }
}
