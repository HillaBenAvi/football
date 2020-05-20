package com.football.DataBase;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

@Repository
public class DBConnector {
    int x;
    public Connection conn;
    public DBConnector() {
        //  conn=makeConnection();

            conn=makeConnection();

    }


    public static final String URL = "jdbc:mysql://localhost:3306/football";
    public static final String USER = "root";
    public static final String PASS = "noa112233123";



//        private static final DBConnector instance = new DBConnector();
       // public Connection conn=makeConnection();
        //private constructor to avoid client applications to use constructor
        //public static DBConnector getInstance(){
//            return instance;
//        }
        /**
         * Get a connection to database
         *
         * @return Connection object
         */

        public Connection getConnection() {
            return conn;
        }


        public Connection makeConnection() {
            System.out.println("i enter to make connection");
            try {
                conn = DriverManager.getConnection(URL,USER,PASS);//DriverManager.getConnection("jdbc:mysql://132.72.65.124:3306/football?" +"user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC");
                System.out.println("make connection");

                return conn;
            } catch (SQLException ex) {
                throw new RuntimeException("Error connecting to the database", ex);
            }
        }

        private void disconnect(){
            try {
                this.conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


//
//    /**
//     * Test Connection
//     */
//package DataBase;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//    public class DBConnector {
//        // public static final String URL = "jdbc:mysql://132.72.65.124:3306/football";
//        // public static final String USER = "root";
//        //public static final String PASS = "root";
//
//        private static final DBConnector instance = new DBConnector();
//        public static Connection conn;//=makeConnection();
//        //private constructor to avoid client applications to use constructor
//        public static DBConnector getInstance(){
//            return instance;
//        }
//
//        private DBConnector() {
//            conn=makeConnection();
//        }
//        /**
//         * Get a connection to database
//         *
//         * @return Connection object
//         */
//
//        public static Connection getConnection() {
//            return conn;
//        }
//
//
//        public static Connection makeConnection() {
//            System.out.println("i enter to make connection");
//            try {
//                conn = DriverManager.getConnection("jdbc:mysql://132.72.65.124:3306/football?" +"user=root&password=root&useLegacyDatetimeCode=false&serverTimezone=UTC");
//                return conn;
//            } catch (SQLException ex) {
//                throw new RuntimeException("Error connecting to the database", ex);
//            }
//        }
//
//        private void disconnect(){
//            try {
//                this.conn.close();
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//
//    }

}
