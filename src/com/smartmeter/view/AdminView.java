package com.smartmeter.view;

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
        System.out.println("2. Delete User");
        System.out.println("3. Calculate Bill (kWh)");
        System.out.println("4. Add Admin");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
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
