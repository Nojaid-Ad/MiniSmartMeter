package com.smartmeter.service.impl;

import com.smartmeter.dao.*;
import com.smartmeter.dao.impl.*;
import com.smartmeter.model.User;
import com.smartmeter.patterns.factory.PaymentFactory;
import com.smartmeter.patterns.factory.PaymentMethod;
import com.smartmeter.patterns.template.*;
import com.smartmeter.service.UserService;

import java.sql.Connection;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.MeterReading;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final BillDAO billDAO = new BillDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final MeterReadingDAO meterDAO = new MeterReadingDAOImpl();

    @Override
    public boolean registerUser(String username, String password) {
        return userDAO.addUser(new User(0, username, password, 0.0, null));
    }

    @Override
    public User login(String username, String password) {
        User u = userDAO.getUserByUsername(username);
        return (u != null && u.getPassword().equals(password)) ? u : null;
    }

    @Override
    public boolean rechargeBalance(int userId, double amount) {
        if (amount <= 0) {
            return false;
        }
        User u = userDAO.getUserById(userId);
        return userDAO.updateBalance(userId, u.getBalance() + amount);
    }

    @Override
    public boolean submitMeterReading(int userId, double reading) {
        if (reading < 0) {
            return false;
        }
        return meterDAO.addReading(userId, reading);
    }

    @Override
    public double calculateConsumption(int userId, double newReading) {

        MeterReading last = meterDAO.getLastReading(userId);

        if (last == null) {
            return newReading;
        }

        if (newReading < last.getReading()) {
            return -1;
        }

        return newReading - last.getReading();
    }

    @Override
    public boolean hasUnpaidConsumption(int userId) {
        return meterDAO.hasUnbilledConsumption(userId);
    }

    @Override
    public int generateBill(int userId, int billingType) {
        AbstractBillGenerator generator = switch (billingType) {
            case 1 ->
                new NormalBillGenerator();
            case 2 ->
                new PeakBillGenerator();
            case 3 ->
                new WeekendBillGenerator();
            default ->
                throw new IllegalArgumentException("Invalid billing type");
        };
        return generator.generateBill(userId);
    }

    @Override
    public double getBillAmount(int billId) {
        return billDAO.getBillAmount(billId);
    }

    @Override
    public boolean payBill(int userId, int billId, String paymentMethod) {

        try {
            Connection c = DBConnection.getInstance().getConnection();
            c.setAutoCommit(false);

            User user = userDAO.getUserById(userId);
            double amount = billDAO.getBillAmount(billId);

            if (user.getBalance() < amount) {
                return false;
            }

            PaymentMethod method = PaymentFactory.createPaymentMethod(paymentMethod);
            if (!method.pay(amount)) {
                return false;
            }

            userDAO.updateBalance(userId, user.getBalance() - amount);
            billDAO.markAsPaid(billId);
            paymentDAO.savePayment(userId, billId, amount, paymentMethod);

            c.commit();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean payGeneratedBill(int userId, int billId, String paymentMethod) {
        try {
            Connection c = DBConnection.getInstance().getConnection();
            try {

                c.setAutoCommit(false);

                User user = userDAO.getUserById(userId);
                double amount = billDAO.getBillAmount(billId);

                if (user.getBalance() < amount) {
                    return false;
                }

                PaymentMethod method = PaymentFactory.createPaymentMethod(paymentMethod);
                if (!method.pay(amount)) {
                    return false;
                }

                userDAO.updateBalance(userId, user.getBalance() - amount);
                billDAO.markAsPaid(billId);
                paymentDAO.savePayment(userId, billId, amount, paymentMethod);

                c.commit();
                return true;

            } catch (Exception e) {
                try {
                    c.rollback();
                } catch (Exception ignored) {
                }
                return false;
            } finally {
                try {
                    c.setAutoCommit(true);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}
