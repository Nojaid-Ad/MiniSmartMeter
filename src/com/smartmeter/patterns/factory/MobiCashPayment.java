package com.smartmeter.patterns.factory;

public class MobiCashPayment implements PaymentMethod {

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + " using MobiCash");
        return true;
    }
}
