package com.smartmeter.service;

public interface MeterReadingService {

    boolean addReading(int userId, double reading);

    double calculateConsumption(int userId, double newReading);
}
