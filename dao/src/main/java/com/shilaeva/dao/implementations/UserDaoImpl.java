package com.shilaeva.dao.implementations;

import com.shilaeva.dao.UserDao;
import com.shilaeva.entities.User;
import com.shilaeva.executors.Executor;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Executor executor;

    public UserDaoImpl() {
        this.executor = new Executor();
    }

    public User getUserById(long id) throws SQLException {
        return executor.execQuery("select * from users where id = " + id, result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public User getUserByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login = '" + login + "'", result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public void insertUser(User user) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" +
                user.getLogin() + "', '" + user.getPassword() + "')");
    }

    public void updateUser(User user) throws SQLException {
        executor.execUpdate("update users set login = '" + user.getLogin()
                + "' where id = " + user.getId());
    }

    public void deleteUser(long id) throws SQLException {
        executor.execUpdate("delete from users where id = " + id);
    }

    public void createTable() throws SQLException {
        executor.execUpdate(
                "create table if not exists users (" +
                        "id bigserial primary key, " +
                        "login varchar(256) unique not null, " +
                        "password varchar(256) not null)");
    }

    public void deleteTable() throws SQLException {
        executor.execUpdate("drop table if exists users");
    }
}

