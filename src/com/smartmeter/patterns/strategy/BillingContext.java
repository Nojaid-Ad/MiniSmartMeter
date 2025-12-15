package com.smartmeter.patterns.strategy;

public class BillingContext {

    private BillingStrategy strategy;

    public void setStrategy(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double kwh) {
        return strategy.calculate(kwh);
    }
}
