package com.smartmeter.service;

public interface BillingService {

    int generateBill(int userId, int billingType);

    boolean payExistingBill(int userId, int billId, String paymentMethod);
}
