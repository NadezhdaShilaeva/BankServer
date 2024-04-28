package com.shilaeva.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionUtil {
    private static Connection connection;

    private ConnectionUtil() {}

    public static Connection getConnection() {
        if (connection == null) {
            connection = getPgSqlConnection();
        }

        return connection;
    }

    private static Connection getPgSqlConnection() {
        try {
            ConnectionConfig config = new ConnectionConfig();

            DriverManager.registerDriver((Driver) Class.forName(config.getDriverClassName())
                    .getDeclaredConstructor().newInstance());

            StringBuilder connectionUrl = new StringBuilder();

            connectionUrl.
                    append(config.getUrl()).
                    append("?user=").
                    append(config.getUsername()).
                    append("&password=").
                    append(config.getPassword());

            return DriverManager.getConnection(connectionUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
