package com.smartmeter.patterns.factory;

public class PayPalPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
        return true;
    }
}
