package com.smartmeter.patterns.factory;

public class LibiPayPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using LibiPay");
        return true;
    }
}
