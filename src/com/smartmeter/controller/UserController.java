package com.smartmeter.controller;

import com.smartmeter.model.User;
import com.smartmeter.service.UserService;
import com.smartmeter.service.impl.UserServiceImpl;
import java.util.InputMismatchException;

import java.util.Scanner;

public class UserController {

    private final UserService userService;
    private final Scanner scanner;

    public UserController() {
        this.userService = new UserServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Smart Meter System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 ->
                    register();
                case 2 ->
                    login();
                case 0 -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default ->
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = userService.registerUser(username, password);

        if (success) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user != null) {
            System.out.println("Login successful. Welcome " + user.getUsername());
            userMenu(user);
        } else {
            System.out.println("Login failed.");
        }
    }

    private void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Recharge Balance");
            System.out.println("3. Pay Bill");
            System.out.println("0. Logout");
            System.out.print("Choose: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 ->
                    System.out.println("Balance: " + user.getBalance());
                case 2 ->
                    user = recharge(user);
                case 3 ->
                    user = payBill(user);
                case 0 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default ->
                    System.out.println("Invalid choice.");
            }
        }
    }

    private User recharge(User user) {
        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        boolean success = userService.rechargeBalance(user.getId(), amount);

        if (success) {
            System.out.println("Balance recharged.");
            return userService.login(user.getUsername(), user.getPassword());
        } else {
            System.out.println("Recharge failed.");
            return user;
        }
    }

    private User payBill(User user) {
        System.out.print("Bill amount: ");
        double amount;

        try {
            amount = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            scanner.nextLine();
            return user;
        }

        System.out.println("Choose payment method:");
        System.out.println("1. Visa");
        System.out.println("2. PayPal");
        System.out.println("3. LibiPay");
        System.out.println("4. MobiCash");
        System.out.print("Choice: ");

        int methodChoice;

        if (scanner.hasNextInt()) {
            methodChoice = scanner.nextInt();
        } else {
            System.out.println("Please enter a valid number");
            scanner.nextLine();
            return user;
        }
        scanner.nextLine();

        String paymentMethod = switch (methodChoice) {
            case 1 ->
                "Visa";
            case 2 ->
                "PayPal";
            case 3 ->
                "LibiPay";
            case 4 ->
                "MobiCash";
            default ->
                null;
        };

        if (paymentMethod == null) {
            System.out.println("Invalid payment method.");
            return user;
        }

        boolean success = userService.payBill(user.getId(), amount, paymentMethod);

        if (success) {
            System.out.println("Bill paid successfully.");
            return userService.login(user.getUsername(), user.getPassword());
        } else {
            System.out.println("Payment failed.");
            return user;
        }
    }
}
