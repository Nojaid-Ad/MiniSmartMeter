package com.smartmeter.patterns.strategy;

public class PeakBillingStrategy implements BillingStrategy {

    @Override
    public double calculate(double khw) {
        return khw * 0.25;
    }
}
