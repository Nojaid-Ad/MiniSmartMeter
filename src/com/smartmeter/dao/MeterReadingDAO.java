package com.smartmeter.dao;

import com.smartmeter.model.MeterReading;

public interface MeterReadingDAO {

    boolean addReading(int userId, double reading);

    MeterReading getLastReading(int userId);

    MeterReading getPreviousReading(int userId);

    boolean hasUnbilledConsumption(int userId);

    boolean markAsBilled(int meterReadingId);

}
