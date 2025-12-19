package com.smartmeter.service.impl;

import com.smartmeter.dao.*;
import com.smartmeter.dao.impl.*;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.MeterReading;
import com.smartmeter.model.Report;
import com.smartmeter.model.User;
import com.smartmeter.patterns.factory.PaymentFactory;
import com.smartmeter.patterns.factory.PaymentMethod;
import com.smartmeter.patterns.observer.LogObserver;
import com.smartmeter.patterns.observer.LogSubject;
import com.smartmeter.patterns.template.*;
import com.smartmeter.service.UserService;

import java.sql.Connection;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final BillDAO billDAO = new BillDAOImpl();
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final MeterReadingDAO meterDAO = new MeterReadingDAOImpl();
    private final LogSubject logSubject = new LogSubject();
    private final ReportDAO reportDAO = new ReportDAOImpl();

    public UserServiceImpl() {
        logSubject.attach(new LogObserver());
    }

    @Override
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public boolean registerUser(String username, String password) {

        boolean ok = userDAO.addUser(
                new User(0, username, password, 0.0, null)
        );

        if (ok) {
            logSubject.notifyObservers(
                    "User registered: " + username,
                    null
            );
        }
        return ok;
    }

    @Override
    public User login(String username, String password) {
        User u = userDAO.getUserByUsername(username);

        if (u != null && u.getPassword().equals(password)) {
            logSubject.notifyObservers(
                    "User logged in",
                    u.getId()
            );
            return u;
        }
        return null;
    }

    @Override
    public boolean rechargeBalance(int userId, double amount) {

        if (amount <= 0) {
            return false;
        }

        User u = userDAO.getUserById(userId);
        if (u == null) {
            return false;
        }

        boolean ok = userDAO.updateBalance(
                userId,
                u.getBalance() + amount
        );

        if (ok) {
            logSubject.notifyObservers(
                    "Balance recharged: +" + amount,
                    userId
            );
        }

        return ok;
    }

    @Override
    public boolean submitMeterReading(int userId, double reading) {

        if (reading < 0) {
            return false;
        }

        boolean ok = meterDAO.addReading(userId, reading);

        if (ok) {
            logSubject.notifyObservers(
                    "Meter reading entered: " + reading + " kWh",
                    userId
            );
        }

        return ok;
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

        int billId = generator.generateBill(userId);

        if (billId > 0) {
            logSubject.notifyObservers(
                    "Bill generated. Bill ID = " + billId,
                    userId
            );
        }

        return billId;
    }

    @Override
    public double getBillAmount(int billId) {
        return billDAO.getBillAmount(billId);
    }

    @Override
    public boolean payBill(int userId, int billId, String paymentMethod) {

        Connection c = null;

        try {
            c = DBConnection.getInstance().getConnection();
            c.setAutoCommit(false);

            User user = userDAO.getUserById(userId);
            if (user == null) {
                return false;
            }

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

            logSubject.notifyObservers(
                    "Bill paid. Amount = " + amount + ", Method = " + paymentMethod,
                    userId
            );

            c.commit();
            return true;

        } catch (Exception e) {
            try {
                if (c != null) {
                    c.rollback();
                }
            } catch (Exception ignored) {
            }
            return false;

        } finally {
            try {
                if (c != null) {
                    c.setAutoCommit(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public boolean payGeneratedBill(int userId, int billId, String paymentMethod) {
        return payBill(userId, billId, paymentMethod);
    }

    @Override
    public boolean submitReport(int userId, String title, String message) {

        boolean ok = reportDAO.addReport(
                new Report(0, userId, title, message, null)
        );

        if (ok) {
            logSubject.notifyObservers(
                    "Report submitted: " + title,
                    userId
            );
        }

        return ok;
    }

}
