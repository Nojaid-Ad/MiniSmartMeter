package com.smartmeter.patterns.template;

import com.smartmeter.patterns.strategy.BillingStrategy;
import com.smartmeter.patterns.strategy.PeakBillingStrategy;

public class PeakBillGenerator extends AbstractBillGenerator {

    @Override
    protected BillingStrategy getStrategy() {
        return new PeakBillingStrategy();
    }
}
