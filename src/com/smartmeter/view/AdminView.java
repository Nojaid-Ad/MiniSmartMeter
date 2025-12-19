package com.smartmeter.view;

import com.smartmeter.model.Report;
import com.smartmeter.model.User;
import java.util.List;
import java.util.Scanner;

public class AdminView {

    private final Scanner scanner = new Scanner(System.in);

    public void showAuthMenu() {
        System.out.println("\n--- Admin ---");
        System.out.println("1. Login");
        System.out.println("0. Back");
        System.out.print("Choose: ");
    }

    public void showAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View Users");
        System.out.println("2. Show All Reports");
        System.out.println("3. Delete User");
        System.out.println("4. Calculate Bill (kWh)");
        System.out.println("5. Add Admin");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
    }

    public void viewUsers(List<User> users) {

        if (users.isEmpty()) {
            showMessage("No users found.");
            return;
        }

        showMessage(String.format(
                "%-5s | %-15s | %-10s",
                "ID", "Username", "Balance"
        ));
        showMessage("-------------------------------------");

        for (User u : users) {
            showMessage(String.format(
                    "%-5d | %-15s | %-10.2f",
                    u.getId(),
                    u.getUsername(),
                    u.getBalance()
            ));
        }

    }

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

    public int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public double readDouble(String label) {
        System.out.print(label);
        while (true) {
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public String readString(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
