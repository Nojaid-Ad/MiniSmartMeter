package com.smartmeter.util;

import com.smartmeter.model.Bill;
import com.smartmeter.model.User;

import java.io.FileWriter;
import java.io.IOException;

public class BillTextGenerator {

    public static void generate(Bill bill, User user) {

        String fileName = "bill_" + bill.getId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("===== Smart Meter Bill =====\n");
            writer.write("User       : " + user.getUsername() + "\n");
            writer.write("Bill ID    : " + bill.getId() + "\n");
            writer.write("Consumption: " + bill.getConsumption() + " kWh\n");
            writer.write("Amount     : " + bill.getAmount() + " LYD\n");
            writer.write("Status     : PAID\n");
            writer.write("============================\n");

            System.out.println("TXT bill created: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
