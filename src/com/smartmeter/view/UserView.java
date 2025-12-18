package com.smartmeter.view;

import java.util.Scanner;

public class UserView {

    private final Scanner scanner = new Scanner(System.in);

    public void showAuthMenu() {
        System.out.println("""
                === Smart Meter System ===
                1. Register
                2. Login
                0. Exit
                """);
        System.out.print("Choose: ");
    }

    public void showUserMenu() {
        System.out.println("""
                --- User Menu ---
                1. Check Balance
                2. Recharge Balance
                3. Pay Bill
                4. Enter Meter Reading
                0. Logout
                """);
        System.out.print("Choose: ");
    }

    public void showPaymentMethods() {
        System.out.println("""
                Choose payment method:
                1. Visa
                2. PayPal
                3. LibiPay
                4. MobiCash
                """);
        System.out.print("Choice: ");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
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

    public double readDouble(String msg) {
        System.out.print(msg);
        while (true) {
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public String readString(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
}
