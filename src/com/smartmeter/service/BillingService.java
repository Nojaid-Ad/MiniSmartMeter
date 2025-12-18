package com.smartmeter.service;

public interface BillingService {

    boolean payBill(
            int userId,
            int billingType,
            String paymentMethod
    );
}
