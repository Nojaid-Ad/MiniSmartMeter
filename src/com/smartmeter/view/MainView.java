package com.smartmeter.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

    private final Scanner scanner = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("\n=== Smart Meter System ===");
        System.out.println("1. User");
        System.out.println("2. Admin");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    public int readChoice() {
        int c;
        try {
            c = scanner.nextInt();
            scanner.nextLine();
            return c;
        } catch (InputMismatchException e) {
            return 0;
        }

    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
