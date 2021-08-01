package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.ConnectionGroupManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost/mydbtest";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static Connection connection = null;

    public static Connection getMySQLConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
