package com.wanli.webapp.dao.Impl;

import com.wanli.webapp.dao.BaseDAO;
import com.wanli.webapp.dao.UserDAO;
import com.wanli.webapp.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {

    @Override
    public User getUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE uid = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("uid"));
                    user.setUname(rs.getString("uname"));
                    user.setPassword(rs.getString("password"));
                    user.setUtype(rs.getInt("utype"));
                }
            }
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User(rs.getInt("uid"));
                user.setUname(rs.getString("uname"));
                user.setPassword(rs.getString("password"));
                user.setUtype(rs.getInt("utype"));
                users.add(user);
            }
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void insertUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO users (uname, password, utype) VALUES (?, ?, ?)")) {
            ps.setString(1, user.getUname());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUtype());
            ps.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE users SET uname = ?, password = ?, utype = ? WHERE uid = ?")) {
            ps.setString(1, user.getUname());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getUtype());
            ps.setInt(4, user.getUid());
            ps.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE uid = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE uname = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("uid"));
                    user.setUname(rs.getString("uname"));
                    user.setPassword(rs.getString("password"));
                    user.setUtype(rs.getInt("utype"));
                }
            }
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
