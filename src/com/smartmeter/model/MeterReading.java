package com.smartmeter.model;

import java.time.LocalDateTime;

public class MeterReading {

    private int id;
    private int userId;
    private double reading;
    private LocalDateTime readingDate;

    public MeterReading(int id, int userId, double reading, LocalDateTime readingDate) {
        this.id = id;
        this.userId = userId;
        this.reading = reading;
        this.readingDate = readingDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getReading() {
        return reading;
    }

    public LocalDateTime getReadingDate() {
        return readingDate;
    }
}
