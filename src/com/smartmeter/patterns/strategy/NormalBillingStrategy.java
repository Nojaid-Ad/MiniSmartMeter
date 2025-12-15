package com.smartmeter.patterns.strategy;

public class NormalBillingStrategy implements BillingStrategy {

    @Override
    public double calculate(double kwh) {
        return kwh * 0.1;
    }
}
