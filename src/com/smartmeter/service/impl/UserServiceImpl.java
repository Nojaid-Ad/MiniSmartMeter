package com.smartmeter.service.impl;

import com.smartmeter.dao.BillDAO;
import com.smartmeter.dao.PaymentDAO;
import com.smartmeter.dao.UserDAO;
import com.smartmeter.dao.impl.BillDAOImpl;
import com.smartmeter.dao.impl.PaymentDAOImpl;
import com.smartmeter.dao.impl.UserDAOImpl;
import com.smartmeter.db.DBConnection;
import com.smartmeter.model.User;
import com.smartmeter.patterns.factory.PaymentFactory;
import com.smartmeter.patterns.factory.PaymentMethod;
import com.smartmeter.patterns.observer.LogObserver;
import com.smartmeter.patterns.observer.LogSubject;
import com.smartmeter.service.UserService;

import java.sql.Connection;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PaymentDAO paymentDAO;
    private final BillDAO billDAO;
    private final LogSubject logSubject;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
        this.paymentDAO = new PaymentDAOImpl();
        this.billDAO = new BillDAOImpl();
        this.logSubject = new LogSubject();
        this.logSubject.attach(new LogObserver());
    }

    @Override
    public boolean registerUser(String username, String password) {

        if (userDAO.getUserByUsername(username) != null) {
            return false;
        }

        User user = new User(0, username, password, 0.0, null);
        boolean created = userDAO.addUser(user);

        if (!created) {
            return false;
        }

        User savedUser = userDAO.getUserByUsername(username);
        if (savedUser != null) {
            logSubject.notifyObservers(
                    "New user registered: " + username,
                    savedUser.getId()
            );
        }

        return true;
    }

    @Override
    public User login(String username, String password) {

        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            logSubject.notifyObservers("User logged in", user.getId());
            return user;
        }

        return null;
    }

    @Override
    public boolean rechargeBalance(int userId, double amount) {

        if (amount <= 0) {
            return false;
        }

        User user = userDAO.getUserById(userId);
        if (user == null) {
            return false;
        }

        boolean updated = userDAO.updateBalance(
                userId,
                user.getBalance() + amount
        );

        if (updated) {
            logSubject.notifyObservers(
                    "User recharged balance: +" + amount,
                    userId
            );
        }

        return updated;
    }

    @Override
    public boolean payBill(int userId, double amount, String paymentMethodName) {

        Connection c = DBConnection.getInstance().getConnection();

        try {
            c.setAutoCommit(false);

            User user = userDAO.getUserById(userId);
            if (user == null || user.getBalance() < amount) {
                return false;
            }

            PaymentMethod method
                    = PaymentFactory.createPaymentMethod(paymentMethodName);

            if (method == null || !method.pay(amount)) {
                return false;
            }

            int billId = billDAO.createBill(userId, 0, amount);
            if (billId == -1) {
                c.rollback();
                return false;
            }

            if (!userDAO.updateBalance(userId, user.getBalance() - amount)) {
                c.rollback();
                return false;
            }

            if (!billDAO.markAsPaid(billId)) {
                c.rollback();
                return false;
            }

            if (!paymentDAO.savePayment(
                    userId, billId, amount, paymentMethodName)) {
                c.rollback();
                return false;
            }

            c.commit();

            logSubject.notifyObservers(
                    "User paid bill #" + billId + " using " + paymentMethodName,
                    userId
            );

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
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
