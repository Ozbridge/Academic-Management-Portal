package org.soft;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseService {
    static final String DB_URL = "jdbc:postgresql://localhost/aims";
    static final String USER = "rishabhjain";
    static final String PASSWORD = "rishabhjain";
    static ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl(DB_URL);
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD);
            cpds.setInitialPoolSize(5);
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(99);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
