package com.smartmeter.dao;

import com.smartmeter.model.Bill;

public interface BillDAO {

    Bill getBillById(int billId);

    int createBill(int userId, int meterReadingId, double consumption, double amount);

    boolean markAsPaid(int billId);

    double getBillAmount(int billId);

    Bill getUnpaidBillByUser(int userId);
}
