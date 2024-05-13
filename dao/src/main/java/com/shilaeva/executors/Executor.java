package com.shilaeva.executors;

import com.shilaeva.utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    public Executor() {}

    public void execUpdate(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public <T> T execQuery(PreparedStatement preparedStatement, ResultHandler<T> handler) throws SQLException {
        ResultSet result = preparedStatement.executeQuery();
        T value = handler.handle(result);

        result.close();
        preparedStatement.close();

        return value;
    }
}
