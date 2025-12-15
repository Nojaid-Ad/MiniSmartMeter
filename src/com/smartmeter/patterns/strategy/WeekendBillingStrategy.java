package com.smartmeter.patterns.strategy;

public class WeekendBillingStrategy implements BillingStrategy {

    @Override
    public double calculate(double kwh) {
        return kwh * 0.05;
    }
}
