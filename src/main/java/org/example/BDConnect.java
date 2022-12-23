package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnect {
    private static BDConnect instance;
    private BDConnect() {}
    public static BDConnect getInstance(){
        if (instance == null){
            instance = new BDConnect();
        }
        return instance;
    }
    public Connection getConnection(String url, String user, String pswrd){
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, pswrd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
