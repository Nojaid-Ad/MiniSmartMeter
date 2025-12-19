package com.smartmeter.service;

import com.smartmeter.model.User;

public interface UserService {

    boolean registerUser(String username, String password);

    User login(String username, String password);

    boolean rechargeBalance(int userId, double amount);

    int generateBill(int userId, int billingType);

    boolean payBill(int userId, int billId, String paymentMethod);

    boolean submitMeterReading(int userId, double reading);

    double calculateConsumption(int userId, double reading);

    boolean hasUnpaidConsumption(int userId);

    double getBillAmount(int billId);

    boolean payGeneratedBill(int userId, int billId, String paymentMethod);

    User getUserById(int userId);

    boolean submitReport(int userId, String title, String message);

}
