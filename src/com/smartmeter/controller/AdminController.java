package com.smartmeter.controller;

import com.smartmeter.model.Admin;
import com.smartmeter.service.AdminService;
import com.smartmeter.service.ReportService;

import com.smartmeter.service.impl.AdminServiceImpl;
import com.smartmeter.service.impl.ReportServiceImpl;
import com.smartmeter.view.AdminView;

public class AdminController {

    private final AdminService adminService;
    private ReportService reportService;
    private final AdminView view;

    public AdminController() {
        this.adminService = new AdminServiceImpl();
        this.reportService = new ReportServiceImpl();
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
                    view.viewUsers(adminService.getAllUsers());
                case 2 ->
                    view.showReports(reportService.getAllReports());
                case 3 ->
                    deleteUser();
                case 4 -> {
                    double kwh = view.readDouble("kWh: ");
                    view.showMessage("1. Normal\n2. Peak\n3. Weekend");
                    int type = view.readInt();

                    double cost = adminService.calculateBill(kwh, type);
                    view.showMessage(kwh + " kWh -> " + cost + " LYD");
                }
                case 5 ->
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

    private void deleteUser() {
        int userId = view.readInt();
        boolean success = adminService.deleteUser(userId);
        view.showMessage(success ? "User deleted." : "Delete failed.");
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
