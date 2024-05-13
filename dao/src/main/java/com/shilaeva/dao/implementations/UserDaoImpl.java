package com.shilaeva.dao.implementations;

import com.shilaeva.dao.UserDao;
import com.shilaeva.entities.User;
import com.shilaeva.executors.Executor;
import com.shilaeva.utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private final Executor executor;

    public UserDaoImpl() {
        this.executor = new Executor();
    }

    public User getUserById(long id) throws SQLException {
        return executor.execQuery(buildQueryGetUserById(id), result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    private PreparedStatement buildQueryGetUserById(long id) throws SQLException {
        String sql = "select * from users where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }

    public User getUserByLogin(String login) throws SQLException {
        return executor.execQuery(buildQueryGetUserByLogin(login), result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    private PreparedStatement buildQueryGetUserByLogin(String login) throws SQLException {
        String sql = "select * from users where login = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, login);

        return preparedStatement;
    }

    public void insertUser(User user) throws SQLException {
        executor.execUpdate(buildQueryInsertUser(user.getLogin(), user.getPassword()));
    }

    private PreparedStatement buildQueryInsertUser(String login, String password) throws SQLException {
        String sql = "insert into users (login, password) values (?, ?)";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        return preparedStatement;
    }

    public void updateUser(User user) throws SQLException {
        executor.execUpdate(buildQueryUpdateUser(user.getLogin(), user.getPassword()));
    }

    private PreparedStatement buildQueryUpdateUser(String login, String password) throws SQLException {
        String sql = "update users set login = ? where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);

        return preparedStatement;
    }

    public void deleteUser(long id) throws SQLException {
        executor.execUpdate(buildQueryDeleteUser(id));
    }

    private PreparedStatement buildQueryDeleteUser(long id) throws SQLException {
        String sql = "delete from users where id = ?";

        PreparedStatement preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }

    public void createTable() throws SQLException {
        executor.execUpdate(buildQueryCreateTable());
    }

    private PreparedStatement buildQueryCreateTable() throws SQLException {
        String sql = "create table if not exists users (" +
                "id bigserial primary key, " +
                "login varchar(256) unique not null, " +
                "password varchar(256) not null)";

        return ConnectionUtil.getConnection().prepareStatement(sql);
    }

    public void deleteTable() throws SQLException {
        executor.execUpdate(buildQueryDeleteTable());
    }

    private PreparedStatement buildQueryDeleteTable() throws SQLException {
        String sql = "drop table if exists users";

        return ConnectionUtil.getConnection().prepareStatement(sql);
    }
}

