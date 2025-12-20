package com.smartmeter.util;

import com.smartmeter.model.Bill;
import com.smartmeter.model.User;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillTextGenerator {

    public static void generate(Bill bill, User user) {
        String fileName = "bill_" + bill.getId() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.write("=========================================\n");
            writer.write("      ELECTRICITY BILL STATEMENT     \n");
            writer.write("=========================================\n");
            writer.write("      Bill Date & Time : " + now.format(formatter) + "\n");
            writer.write("\n\n");
            writer.write("      User Name    : " + user.getUsername() + "\n");
            writer.write("      Bill Number      : " + bill.getId() + "\n");
            writer.write("\n\n");
            writer.write("      Meter Consumption Details\n");
            writer.write("\n\n");
            writer.write("      Total Usage      : " + bill.getConsumption() + " kWh\n");
            writer.write("      Total Amount     : " + bill.getAmount() + " LYD\n");
            writer.write("\n\n");
            writer.write("      Payment Status   : PAID\n");
            writer.write("(\"=========================================\\\n");
            writer.write("      System Generated Electricity Bill Successfully.\n");
            writer.write("      Please keep this bill for your records.\n");
            writer.write("=========================================\n");
            System.out.println("TXT bill created: " + fileName);
        } catch (Exception e) {
            System.out.println("File not generated");
        }
    }
}
