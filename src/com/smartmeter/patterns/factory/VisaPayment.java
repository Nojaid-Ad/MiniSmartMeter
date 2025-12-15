package com.smartmeter.patterns.factory;

public class VisaPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using Visa");
        return true;
    }
}
