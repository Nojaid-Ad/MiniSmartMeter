package com.smartmeter.service.impl;

import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.dao.impl.MeterReadingDAOImpl;
import com.smartmeter.model.MeterReading;
import com.smartmeter.service.MeterReadingService;

public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingDAO dao;

    public MeterReadingServiceImpl() {
        this.dao = new MeterReadingDAOImpl();
    }

    @Override
    public boolean addReading(int userId, double reading) {
        return dao.addReading(userId, reading);
    }

    @Override
    public double calculateConsumption(int userId, double newReading) {

        MeterReading last = dao.getLastReading(userId);

        if (last == null) {
            return newReading;
        }

        return newReading - last.getReading();
    }
}
