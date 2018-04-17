package ru.otus.l091.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    private static Connection connection = null;

    private ConnectionHelper() {
        throw new UnsupportedOperationException("Private constructor");
    }

    public static Connection getConnection() {
        if (connection == null) {
            connection = initConnection();
        }
        return connection;
    }

    private static Connection initConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +
                    "localhost:" +
                    "3306/" +
                    "dbexample?" +
                    "user=root&" +
                    "password=root&" +
                    "useSSL=false";


            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

