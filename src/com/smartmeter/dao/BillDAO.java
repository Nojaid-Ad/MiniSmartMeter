package com.smartmeter.dao;

public interface BillDAO {

    int createBill(int userId, int meterReadingId, double consumption, double amount);

    boolean markAsPaid(int billId);

    double getBillAmount(int billId);
}
