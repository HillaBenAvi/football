package com.football.DataBase;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnector {
    public static final String URL = "jdbc:mysql://localhost:3306/football";
    public static final String USER = "root";
    public static final String PASS = "noa112233123";

    private static final DBConnector instance = new DBConnector();

    //private constructor to avoid client applications to use constructor
    public static DBConnector getInstance(){
        return instance;
    }

    private DBConnector() {

    }
    /**
     * Get a connection to database
     *
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            //  DriverManager.registerDriver(new Driver());
            // return  DriverManager.getConnection(URL, USER, PASS);
            Connection conn =
                    DriverManager.getConnection(URL,USER,PASS);
            return conn;
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }


    /**
     * Test Connection
     */


}
