package com.smartmeter.service;

import com.smartmeter.model.Admin;
import com.smartmeter.model.User;
import java.util.List;

public interface AdminService {

    Admin login(String username, String password);

    boolean addAdmin(String username, String password);

    boolean deleteUser(int userId);

    List<User> getAllUsers();

    double calculateBill(double kwh);
}
