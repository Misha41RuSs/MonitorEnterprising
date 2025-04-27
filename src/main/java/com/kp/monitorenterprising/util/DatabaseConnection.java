package com.kp.monitorenterprising.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/MISPIS?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "DISTER07";
        return DriverManager.getConnection(url, user, password);
    }
}
