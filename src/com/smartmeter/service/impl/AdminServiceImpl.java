package com.smartmeter.service.impl;

import com.smartmeter.dao.AdminDAO;
import com.smartmeter.dao.impl.AdminDAOImpl;
import com.smartmeter.model.Admin;
import com.smartmeter.model.User;
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

    @Override
    public double calculateBill(double kwh) {
        return kwh / 10.0;
    }
}
