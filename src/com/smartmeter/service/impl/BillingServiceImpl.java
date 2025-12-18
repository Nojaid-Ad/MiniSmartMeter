package com.smartmeter.service.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.dao.UserDAO;
import com.smartmeter.dao.impl.BillDAOImpl;
import com.smartmeter.dao.impl.MeterReadingDAOImpl;
import com.smartmeter.dao.impl.UserDAOImpl;
import com.smartmeter.model.MeterReading;
import com.smartmeter.model.User;
import com.smartmeter.patterns.factory.PaymentFactory;
import com.smartmeter.patterns.strategy.BillingContext;
import com.smartmeter.patterns.strategy.NormalBillingStrategy;
import com.smartmeter.patterns.strategy.PeakBillingStrategy;
import com.smartmeter.patterns.strategy.WeekendBillingStrategy;
import com.smartmeter.service.BillingService;

public class BillingServiceImpl implements BillingService {

    private final BillDAO billDAO = new BillDAOImpl();
    private final MeterReadingDAO meterReadingDAO = new MeterReadingDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private final BillingContext billingContext = new BillingContext();

    @Override
    public boolean payBill(int userId, int billingType, String paymentMethod) {

        MeterReading reading = meterReadingDAO.getLastReading(userId);
        if (reading == null) {
            System.out.println("No meter reading found");
            return false;
        }

        switch (billingType) {
            case 1 ->
                billingContext.setStrategy(new NormalBillingStrategy());
            case 2 ->
                billingContext.setStrategy(new PeakBillingStrategy());
            case 3 ->
                billingContext.setStrategy(new WeekendBillingStrategy());
            default -> {
                System.out.println("Invalid billing type");
                return false;
            }
        }

        double consumption = reading.getReading();
        double amount = billingContext.calculate(consumption);

        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        if (user.getBalance() < amount) {
            System.out.println("Insufficient balance");
            return false;
        }

        boolean paid = PaymentFactory
                .createPaymentMethod(paymentMethod)
                .pay(amount);

        if (!paid) {
            System.out.println("Payment failed");
            return false;
        }

        int billId = billDAO.createBill(
                userId,
                reading.getId(),
                consumption,
                amount
        );

        if (billId == -1) {
            System.out.println("Failed to create bill");
            return false;
        }

        userDAO.updateBalance(userId, user.getBalance() - amount);
        billDAO.markAsPaid(billId);

        System.out.println("Bill paid successfully");
        System.out.println("Amount: " + amount);

        return true;
    }
}
