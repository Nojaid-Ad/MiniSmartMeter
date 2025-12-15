package com.smartmeter.view;

import java.util.Scanner;

public class UserView {

    private final Scanner scanner = new Scanner(System.in);

    public void showAuthMenu() {
        System.out.println("\n--- User ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Back");
        System.out.print("Choose: ");
    }

    public void showUserMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Recharge Balance");
        System.out.println("3. Pay Bill");
        System.out.println("0. Logout");
        System.out.print("Choose: ");
    }

    public String readString(String label) {
        System.out.print(label);
        return scanner.nextLine();
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

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
