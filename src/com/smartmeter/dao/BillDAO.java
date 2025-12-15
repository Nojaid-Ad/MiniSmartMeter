package com.smartmeter.dao;

public interface BillDAO {
    int createBill(int userId, double consumption, double amount);
    boolean markAsPaid(int billId);
}
