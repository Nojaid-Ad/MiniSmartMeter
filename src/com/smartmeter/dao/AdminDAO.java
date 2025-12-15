package com.smartmeter.dao;

import com.smartmeter.model.Admin;
import com.smartmeter.model.User;
import java.util.List;

public interface AdminDAO {

    Admin login(String username, String password);

    boolean deleteUser(int userId);

    List<User> getAllUsers();
}
