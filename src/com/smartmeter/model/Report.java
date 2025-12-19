package com.smartmeter.model;

import java.time.LocalDateTime;

public class Report {

    private int id;
    private int userId;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    public Report(int id, int userId, String title, String message, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Report(int userId, String title, String message) {
        this.userId = userId;
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
