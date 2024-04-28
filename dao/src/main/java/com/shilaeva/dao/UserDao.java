package com.shilaeva.dao;

import com.shilaeva.entities.User;

import java.sql.SQLException;

public interface UserDao {
    User getUserById(long id) throws SQLException;
    User getUserByLogin(String login) throws SQLException;
    void insertUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(long id) throws SQLException;
    void createTable() throws SQLException;
    void deleteTable() throws SQLException;
}
