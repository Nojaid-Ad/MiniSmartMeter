package com.smartmeter.patterns.strategy;

public interface BillingStrategy {
    double calculate(double consumption);
}
