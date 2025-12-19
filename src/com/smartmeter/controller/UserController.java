package com.smartmeter.controller;

import com.smartmeter.patterns.facade.BillingFacade;
import com.smartmeter.model.Bill;
import com.smartmeter.model.User;
import com.smartmeter.service.UserService;
import com.smartmeter.service.BillingService;
import com.smartmeter.service.impl.UserServiceImpl;
import com.smartmeter.service.impl.BillingServiceImpl;
import com.smartmeter.view.UserView;
import com.smartmeter.view.BillView;

public class UserController {

    private final UserService userService = new UserServiceImpl();
    private final BillingService billingService = new BillingServiceImpl();
    private final BillingFacade billingFacade = new BillingFacade();
    private final UserView view = new UserView();
    private final BillView billView = new BillView();

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
                    view.showMessage("Balance: " + user.getBalance() + "LYD");

                case 2 -> {
                    if (billingFacade.getCurrentBill(user.getId()) != null) {
                        view.showMessage("You must pay the current bill first");
                        break;
                    }

                    double reading = view.readDouble("Enter meter reading (kWh): ");
                    double consumption = userService.calculateConsumption(user.getId(), reading);

                    if (consumption < 0) {
                        view.showMessage("Error: New reading cannot be less than previous reading");
                        break;
                    }

                    if (userService.submitMeterReading(user.getId(), reading)) {
                        view.showMessage("Reading saved successfully");
                        view.showMessage("Consumption: " + consumption + " kWh");

                        view.showMessage("""
                            Choose billing type:
                            1. Normal
                            2. Peak
                            3. Weekend
                        """);

                        int billingType = view.readInt();
                        int billId = billingService.generateBill(user.getId(), billingType);

                        if (billId != -1) {
                            view.showMessage("Bill generated successfully");
                        } else {
                            view.showMessage("Failed to generate bill");
                        }
                    }
                }

                case 3 -> {
                    Bill bill = billingFacade.getCurrentBill(user.getId());
                    if (bill == null) {
                        view.showMessage("No unpaid bill found");
                    } else {
                        billView.showCurrentBill(bill);
                    }
                }

                case 4 -> {
                    Bill bill = billingFacade.getCurrentBill(user.getId());
                    if (bill == null) {
                        view.showMessage("No unpaid bill to pay");
                        break;
                    }

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
                        break;
                    }

                    boolean success = billingService.payExistingBill(
                            user.getId(),
                            bill.getId(),
                            paymentMethod
                    );

                    if (success) {
                        view.showMessage("Payment completed successfully");
                        user = userService.getUserById(user.getId());
                    } else {
                        view.showMessage("Payment failed");
                    }
                }

                case 5 -> {
                    double amt = view.readDouble("Amount: ");
                    if (userService.rechargeBalance(user.getId(), amt)) {
                        user = userService.login(user.getUsername(), user.getPassword());
                        view.showMessage("Balance recharged");
                    } else {
                        view.showMessage("Invalid amount");
                    }
                }
                case 6 -> {
                    String title = view.readString("Report title: ");
                    String message = view.readString("Message: ");

                    if (userService.submitReport(user.getId(), title, message)) {
                        view.showMessage("Report submitted successfully");
                    } else {
                        view.showMessage("Failed to submit report");
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
}
