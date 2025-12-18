package com.smartmeter.patterns.template;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.dao.impl.BillDAOImpl;
import com.smartmeter.dao.impl.MeterReadingDAOImpl;
import com.smartmeter.model.MeterReading;
import com.smartmeter.patterns.strategy.BillingStrategy;

public abstract class AbstractBillGenerator {

    protected MeterReadingDAO meterDAO = new MeterReadingDAOImpl();
    protected BillDAO billDAO = new BillDAOImpl();

    public final int generateBill(int userId) {

        MeterReading last = meterDAO.getLastReading(userId);
        if (last == null) {
            throw new RuntimeException("No meter reading found");
        }

        double consumption = calculateConsumption(userId, last.getReading());
        if (consumption <= 0) {
            throw new RuntimeException("Invalid consumption");
        }

        BillingStrategy strategy = getStrategy();
        double amount = strategy.calculate(consumption);

        return billDAO.createBill(
                userId,
                last.getId(),
                consumption,
                amount
        );
    }

    protected double calculateConsumption(int userId, double currentReading) {
        MeterReading prev = meterDAO.getLastReading(userId);
        if (prev == null) {
            return currentReading;
        }
        return currentReading - prev.getReading();
    }

    protected abstract BillingStrategy getStrategy();
}
