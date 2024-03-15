package com.wanli.webapp.dao;

import com.wanli.webapp.entities.User;

import java.util.List;
public interface UserDAO {
    User getUser(int id);
    List<User> getAllUsers();
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    User getUserByUsername(String username);
}

