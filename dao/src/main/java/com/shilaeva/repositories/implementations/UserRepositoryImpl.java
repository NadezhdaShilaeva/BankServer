package com.shilaeva.repositories.implementations;

import com.shilaeva.dao.UserDao;
import com.shilaeva.entities.User;
import com.shilaeva.repositories.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
        createTable();
    }

    @Override
    public User getUserById(long id) {
        try {
            return userDao.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            return userDao.getUserByLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void create(User user) {
        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            userDao.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            userDao.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
