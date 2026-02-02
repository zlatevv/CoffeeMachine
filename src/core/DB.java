package core;

import java.sql.*;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/coffee_machine";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
