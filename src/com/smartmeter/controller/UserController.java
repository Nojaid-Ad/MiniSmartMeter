package com.smartmeter.controller;

import com.smartmeter.model.User;
import com.smartmeter.service.UserService;
import com.smartmeter.service.impl.UserServiceImpl;
import com.smartmeter.view.UserView;

public class UserController {

    private final UserService userService;
    private final UserView view;

    public UserController() {
        this.userService = new UserServiceImpl();
        this.view = new UserView();
    }

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
                        view.showMessage("Balance recharged");
                        user = userService.login(user.getUsername(), user.getPassword());
                    } else {
                        view.showMessage("Recharge failed");
                    }
                }

                case 3 ->
                    user = payBill(user);

                case 0 -> {
                    return;
                }

                default ->
                    view.showMessage("Invalid choice");
            }
        }
    }

    private User payBill(User user) {

        double amt = view.readDouble("Bill amount: ");

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

        boolean success = userService.payBill(
                user.getId(),
                amt,
                paymentMethod
        );

        if (success) {
            view.showMessage("Bill paid using " + paymentMethod);
            return userService.login(user.getUsername(), user.getPassword());
        } else {
            view.showMessage("Payment failed");
            return user;
        }
    }
}
