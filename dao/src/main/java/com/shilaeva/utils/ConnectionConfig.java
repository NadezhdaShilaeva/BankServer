package com.shilaeva.utils;

public class ConnectionConfig {
    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public ConnectionConfig() {
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();

        this.url = propertiesLoader.getProperty("datasource.url");
        this.username = propertiesLoader.getProperty("datasource.username");
        this.password = propertiesLoader.getProperty("datasource.password");
        this.driverClassName = propertiesLoader.getProperty("datasource.driver-class-name");
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }
}
