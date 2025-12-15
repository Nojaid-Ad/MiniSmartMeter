package com.smartmeter.patterns.strategy;

public class WeekendBillingStrategy implements BillingStrategy {

    private static final double RATE = 0.6;

    @Override
    public double calculate(double consumption) {
        return consumption * RATE;
    }
}
