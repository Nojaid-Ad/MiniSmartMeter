package com.smartmeter.service.impl;

import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.dao.impl.MeterReadingDAOImpl;
import com.smartmeter.model.MeterReading;
import com.smartmeter.service.MeterReadingService;

public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingDAO meterDAO = new MeterReadingDAOImpl();

    @Override
    public boolean submitReading(int userId, double newReading) {
        return meterDAO.addReading(userId, newReading);
    }

    @Override
    public double calculateConsumption(int userId, double newReading) {

        MeterReading last = meterDAO.getLastReading(userId);

        if (last == null) {
            return newReading;
        }
        if (newReading < last.getReading()) {
            return -1;
        }
        return newReading - last.getReading();
    }
}
