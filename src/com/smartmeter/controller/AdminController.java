package com.smartmeter.controller;

import com.smartmeter.model.Admin;
import com.smartmeter.model.User;
import com.smartmeter.service.AdminService;
import com.smartmeter.service.impl.AdminServiceImpl;
import com.smartmeter.view.AdminView;

import java.util.List;

public class AdminController {

    private final AdminService adminService;
    private final AdminView view;

    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.view = new AdminView();
    }

    public void start() {
        while (true) {
            view.showAuthMenu();
            int choice = view.readInt();

            switch (choice) {
                case 1 ->
                    login();
                case 0 -> {
                    view.showMessage("Back to main menu.");
                    return;
                }
                default ->
                    view.showMessage("Invalid choice.");
            }
        }
    }

    private void login() {
        String username = view.readString("Username: ");
        String password = view.readString("Password: ");

        Admin admin = adminService.login(username, password);

        if (admin == null) {
            view.showMessage("Login failed.");
            return;
        }

        view.showMessage("Admin logged in.");
        adminMenu();
    }

    private void adminMenu() {
        while (true) {
            view.showAdminMenu();
            int choice = view.readInt();

            switch (choice) {
                case 1 ->
                    viewUsers();
                case 2 ->
                    deleteUser();
                case 3 ->
                    calculateBill();
                case 4 ->
                    addAdmin();
                case 0 -> {
                    view.showMessage("Logged out.");
                    return;
                }
                default ->
                    view.showMessage("Invalid choice.");
            }
        }
    }

    private void viewUsers() {
        List<User> users = adminService.getAllUsers();

        if (users.isEmpty()) {
            view.showMessage("No users found.");
            return;
        }

        view.showMessage("ID | Username | Balance");
        for (User u : users) {
            view.showMessage(
                    u.getId() + " | " + u.getUsername() + " | " + u.getBalance()
            );
        }
    }

    private void deleteUser() {
        int userId = view.readInt();
        boolean success = adminService.deleteUser(userId);
        view.showMessage(success ? "User deleted." : "Delete failed.");
    }

    private void calculateBill() {
        double kwh = view.readDouble("kWh: ");
        double cost = adminService.calculateBill(kwh);
        view.showMessage(kwh + " kWh -> " + cost + " Dinar");
    }

    private void addAdmin() {
        String username = view.readString("New admin username: ");
        String password = view.readString("Password: ");

        boolean success = adminService.addAdmin(username, password);
        view.showMessage(
                success ? "Admin added successfully." : "Failed to add admin."
        );
    }
}
