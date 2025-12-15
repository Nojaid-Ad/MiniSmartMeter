package com.smartmeter.service.impl;

import com.smartmeter.dao.AdminDAO;
import com.smartmeter.dao.impl.AdminDAOImpl;
import com.smartmeter.model.Admin;
import com.smartmeter.model.User;
import com.smartmeter.patterns.strategy.BillingContext;
import com.smartmeter.patterns.strategy.NormalBillingStrategy;
import com.smartmeter.patterns.strategy.PeakBillingStrategy;
import com.smartmeter.patterns.strategy.WeekendBillingStrategy;
import com.smartmeter.service.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    public AdminServiceImpl() {
        this.adminDAO = new AdminDAOImpl();
    }

    @Override
    public Admin login(String username, String password) {
        return adminDAO.login(username, password);
    }

    @Override
    public boolean addAdmin(String username, String password) {
        return adminDAO.addAdmin(username, password);
    }

    @Override
    public boolean deleteUser(int userId) {
        return adminDAO.deleteUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return adminDAO.getAllUsers();
    }

    private BillingContext billingContext = new BillingContext();

    @Override
    public double calculateBill(double kwh, int type) {

        switch (type) {
            case 1 ->
                billingContext.setStrategy(new NormalBillingStrategy());
            case 2 ->
                billingContext.setStrategy(new PeakBillingStrategy());
            case 3 ->
                billingContext.setStrategy(new WeekendBillingStrategy());
            default ->
                throw new IllegalArgumentException("Invalid billing type");
        }

        return billingContext.calculate(kwh);
    }

}
