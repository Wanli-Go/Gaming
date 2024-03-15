package com.wanli.webapp.utilities;

import com.wanli.webapp.entities.User;

import java.util.List;

public class PasswordChecker {
    public synchronized static boolean check(String username, String password, User user){
        if (user == null) return false;
        return username.equals(user.getUname()) && password.equals(user.getPassword());
    }
}
