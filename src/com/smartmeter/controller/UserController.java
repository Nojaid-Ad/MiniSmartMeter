package com.smartmeter.controller;

import com.smartmeter.model.User;
import com.smartmeter.service.UserService;
import com.smartmeter.service.BillingService;
import com.smartmeter.service.impl.UserServiceImpl;
import com.smartmeter.service.impl.BillingServiceImpl;
import com.smartmeter.view.UserView;

public class UserController {

    private final UserService userService = new UserServiceImpl();
    private final BillingService billingService = new BillingServiceImpl();
    private final UserView view = new UserView();

    public void start() {
        while (true) {
            view.showAuthMenu();
            int choice = view.readInt();

            switch (choice) {
                case 1 ->
                    register();
                case 2 ->
                    login();
                case 0 -> {
                    return;
                }
                default ->
                    view.showMessage("Invalid choice");
            }
        }
    }

    private void register() {
        String u = view.readString("Username: ");
        String p = view.readString("Password: ");

        view.showMessage(
                userService.registerUser(u, p)
                ? "Registration successful"
                : "Registration failed"
        );
    }

    private void login() {
        String u = view.readString("Username: ");
        String p = view.readString("Password: ");

        User user = userService.login(u, p);
        if (user != null) {
            view.showMessage("Welcome " + user.getUsername());
            userMenu(user);
        } else {
            view.showMessage("Login failed");
        }
    }

    private void userMenu(User user) {
        while (true) {
            view.showUserMenu();
            int c = view.readInt();

            switch (c) {
                case 1 ->
                    view.showMessage("Balance: " + user.getBalance());

                case 2 -> {
                    double amt = view.readDouble("Amount: ");
                    if (userService.rechargeBalance(user.getId(), amt)) {
                        user = userService.login(user.getUsername(), user.getPassword());
                        view.showMessage("Balance recharged");
                    }
                }

                case 3 ->
                    user = payBill(user);

                case 4 -> {
                    double reading = view.readDouble("Enter meter reading (kWh): ");
                    double consumption = userService.calculateConsumption(user.getId(), reading);
                    if (consumption < 0) {
                        view.showMessage("Error: New reading cannot be less than previous reading");
                        break;
                    }
                    if (userService.submitMeterReading(user.getId(), reading)) {
                        view.showMessage("Reading saved successfully");
                        view.showMessage("Consumption: " + consumption + " kWh");
                    }
                }

                case 0 -> {
                    return;
                }

                default ->
                    view.showMessage("Invalid choice");
            }
        }
    }

    private User payBill(User user) {
        view.showMessage("""
            Billing type:
            1. Normal
            2. Peak
            3. Weekend
        """);

        int type = view.readInt();

        view.showPaymentMethods();
        int methodChoice = view.readInt();

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
            view.showMessage("Invalid payment method");
            return user;
        }

        boolean success = billingService.payBill(
                user.getId(),
                type,
                paymentMethod
        );

        if (success) {
            view.showMessage("Payment completed successfully");
            return userService.login(user.getUsername(), user.getPassword());
        }

        view.showMessage("Payment failed");
        return user;
    }
}
