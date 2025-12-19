package com.smartmeter.model;
public class Bill {

    private int id;
    private int userId;
    private double consumption;
    private double amount;
    private String status;

    public Bill(int id, int userId, double consumption, double amount, String status) {
        this.id = id;
        this.userId = userId;
        this.consumption = consumption;
        this.amount = amount;
        this.status = status;
    }

    public boolean isPaid() {
        return "PAID".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getConsumption() {
        return consumption;
    }

    public double getAmount() {
        return amount;
    }
}
