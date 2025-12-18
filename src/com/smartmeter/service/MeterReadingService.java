package com.smartmeter.service;

public interface MeterReadingService {

    boolean submitReading(int userId, double newReading);

    double calculateConsumption(int userId, double newReading);
}
