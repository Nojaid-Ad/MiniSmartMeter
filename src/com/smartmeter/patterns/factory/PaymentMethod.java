package com.smartmeter.patterns.factory;

public interface PaymentMethod {
    boolean pay(double amount);
}
