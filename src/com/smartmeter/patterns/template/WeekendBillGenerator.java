package com.smartmeter.patterns.template;

import com.smartmeter.patterns.strategy.BillingStrategy;
import com.smartmeter.patterns.strategy.WeekendBillingStrategy;

public class WeekendBillGenerator extends AbstractBillGenerator {

    @Override
    protected BillingStrategy getStrategy() {
        return new WeekendBillingStrategy();
    }
}
