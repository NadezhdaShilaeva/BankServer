package com.shilaeva.executors;

import com.shilaeva.utils.ConnectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    public Executor() {}

    public void execUpdate(String update) throws SQLException {
        Statement statement = ConnectionUtil.getConnection().createStatement();
        statement.execute(update);
        statement.close();
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement statement = ConnectionUtil.getConnection().createStatement();
        statement.execute(query);

        ResultSet result = statement.getResultSet();
        T value = handler.handle(result);

        result.close();
        statement.close();

        return value;
    }

}
