package com.smartmeter.patterns.strategy;

public class NormalBillingStrategy implements BillingStrategy {

    private static final double RATE = 0.5;

    @Override
    public double calculate(double consumption) {
        return consumption * RATE;
    }
}
