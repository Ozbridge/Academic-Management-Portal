package org.soft;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    static final String DB_URL = "jdbc:postgresql://localhost/aims";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";
    //    static ComboPooledDataSource cpds = new ComboPooledDataSource();
    static Connection con;
    static boolean autoCommit = true;

    static {
        try {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//            cpds.setDriverClass("org.postgresql.Driver");
//            cpds.setJdbcUrl(DB_URL);
//            cpds.setUser(USER);
//            cpds.setPassword(PASSWORD);
//            cpds.setInitialPoolSize(5);
//            cpds.setMinPoolSize(5);
//            cpds.setAcquireIncrement(5);
//            cpds.setMaxPoolSize(99);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int setAutoCommit(boolean val) {
        autoCommit = val;
        return 0;
    }

    static Connection getConnection() {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (!autoCommit)
                con.setAutoCommit(false);
            return con;
//            return cpds.getConnection();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
        return null;
    }
}
