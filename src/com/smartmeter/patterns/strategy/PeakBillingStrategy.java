package com.smartmeter.patterns.strategy;

public class PeakBillingStrategy implements BillingStrategy {

    private static final double RATE = 0.8;

    @Override
    public double calculate(double consumption) {
        return consumption * RATE;
    }
}
