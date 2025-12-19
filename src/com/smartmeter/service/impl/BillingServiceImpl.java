package com.smartmeter.service.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.dao.MeterReadingDAO;
import com.smartmeter.dao.PaymentDAO;
import com.smartmeter.dao.UserDAO;
import com.smartmeter.dao.impl.BillDAOImpl;
import com.smartmeter.dao.impl.MeterReadingDAOImpl;
import com.smartmeter.dao.impl.PaymentDAOImpl;
import com.smartmeter.dao.impl.UserDAOImpl;
import com.smartmeter.model.Bill;
import com.smartmeter.model.MeterReading;
import com.smartmeter.model.User;
import com.smartmeter.patterns.factory.PaymentFactory;
import com.smartmeter.patterns.observer.LogObserver;
import com.smartmeter.patterns.observer.LogSubject;
import com.smartmeter.patterns.strategy.BillingContext;
import com.smartmeter.patterns.strategy.NormalBillingStrategy;
import com.smartmeter.patterns.strategy.PeakBillingStrategy;
import com.smartmeter.patterns.strategy.WeekendBillingStrategy;
import com.smartmeter.service.BillingService;
import com.smartmeter.util.BillTextGenerator;

public class BillingServiceImpl implements BillingService {

    private final BillDAO billDAO = new BillDAOImpl();
    private final MeterReadingDAO meterReadingDAO = new MeterReadingDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final LogSubject logSubject = new LogSubject();
    private final BillingContext billingContext = new BillingContext();

    public BillingServiceImpl() {
        logSubject.attach(new LogObserver());
    }

    @Override
    public int generateBill(int userId, int billingType) {

        Bill unpaid = billDAO.getUnpaidBillByUser(userId);
        if (unpaid != null) {
            System.out.println("There is already an unpaid bill");
            return -1;
        }

        MeterReading last = meterReadingDAO.getLastReading(userId);
        MeterReading prev = meterReadingDAO.getPreviousReading(userId);

        if (last == null) {
            System.out.println("No meter reading found");
            return -1;
        }

        double prevValue = (prev == null) ? 0 : prev.getReading();
        double consumption = last.getReading() - prevValue;

        if (consumption <= 0) {
            System.out.println("No consumption to bill");
            return -1;
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
                return -1;
            }
        }

        double amount = billingContext.calculate(consumption);

        return billDAO.createBill(
                userId,
                last.getId(),
                consumption,
                amount
        );
    }

    @Override
    public boolean payExistingBill(int userId, int billId, String paymentMethod) {

        Bill bill = billDAO.getUnpaidBillByUser(userId);
        if (bill == null || bill.getId() != billId) {
            System.out.println("No unpaid bill to pay");
            return false;
        }

        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        double amount = bill.getAmount();

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

        userDAO.updateBalance(userId, user.getBalance() - amount);
        paymentDAO.savePayment(userId, billId, amount, paymentMethod);
        billDAO.markAsPaid(billId);
        Bill paidBill = billDAO.getBillById(billId);
        User paidUser = userDAO.getUserById(userId);
        BillTextGenerator.generate(paidBill, paidUser);
        return true;
    }
}
