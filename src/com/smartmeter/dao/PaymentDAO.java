package com.smartmeter.dao;

public interface PaymentDAO {

    boolean savePayment(int userId, int billId, double amount, String method);

}
