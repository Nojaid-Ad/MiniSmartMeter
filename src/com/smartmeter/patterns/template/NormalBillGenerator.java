package com.smartmeter.patterns.template;

import com.smartmeter.patterns.strategy.BillingStrategy;
import com.smartmeter.patterns.strategy.NormalBillingStrategy;

public class NormalBillGenerator extends AbstractBillGenerator {

    @Override
    protected BillingStrategy getStrategy() {
        return new NormalBillingStrategy();
    }
}
