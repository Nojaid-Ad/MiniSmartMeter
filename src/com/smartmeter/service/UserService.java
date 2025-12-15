package com.smartmeter.service;

import com.smartmeter.model.User;
import java.util.List;

public interface UserService {

    boolean registerUser(String username, String password);

    User login(String username, String password);

    boolean rechargeBalance(int userId, double amount);

    boolean payBill(int userId, double amount);

    List<User> getAllUsers();
}
