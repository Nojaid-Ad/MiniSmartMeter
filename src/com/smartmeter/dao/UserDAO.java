package com.smartmeter.dao;

import com.smartmeter.model.User;
import java.util.List;

public interface UserDAO {

    boolean addUser(User user);

    User getUserByUsername(String username);

    User getUserById(int id);

    boolean updateBalance(int userId, double newBalance);

    boolean deleteUser(int userId);
}
