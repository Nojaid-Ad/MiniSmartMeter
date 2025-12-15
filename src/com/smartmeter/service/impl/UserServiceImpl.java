package com.smartmeter.service.impl;

import com.smartmeter.dao.UserDAO;
import com.smartmeter.dao.impl.UserDAOImpl;
import com.smartmeter.model.User;
import com.smartmeter.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public boolean registerUser(String username, String password) {
        if (userDAO.getUserByUsername(username) != null) {
            System.out.println("Username already exists.");
            return false;
        }
        User user = new User(0, username, password, 0.0, null);
        return userDAO.addUser(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
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

        double newBalance = user.getBalance() + amount;
        return userDAO.updateBalance(userId, newBalance);
    }

    @Override
    public boolean payBill(int userId, double amount) {
        User user = userDAO.getUserById(userId);
        if (user == null || user.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return false;
        }
        return userDAO.updateBalance(userId, user.getBalance() - amount);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
