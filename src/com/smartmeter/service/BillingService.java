package com.smartmeter.service;

import com.smartmeter.patterns.strategy.BillingStrategy;

public class BillingService {

    private BillingStrategy strategy;

    public void setStrategy(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateBill(double consumption) {
        return strategy.calculate(consumption);
    }
}
