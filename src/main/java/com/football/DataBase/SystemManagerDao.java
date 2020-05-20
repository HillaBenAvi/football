package com.football.DataBase;

import com.football.Domain.League.League;
import com.football.Domain.Users.SystemManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SystemManagerDao implements DAOTEMP<SystemManager> {
    private static final SystemManagerDao instance = new SystemManagerDao();

    //private constructor to avoid client applications to use constructor
    public static SystemManagerDao getInstance(){
        return instance;
    }
    DBConnector dbc;//= DBConnector.getInstance();
    Connection connection;

    @Override
    public String getTableName() {
        return " systemManager ";
    }

    private SystemManagerDao() {

        connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {
        String toReturn="";
        try {
            // Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userName="+id+";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");

                toReturn = userName + ":" + EncryptPassword + ":" + name + ":" + birthDate;
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return toReturn;
    }

    @Override
    public List<String> getAll() {
        LinkedList<String> allTheTable = new LinkedList<>();
        try {
            //Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From " + getTableName()+ ";";
            System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");

                String toReturn = userName + ":" + EncryptPassword + ":" + name + ":" + birthDate;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }

    @Override
    public void save(SystemManager systemManager){
        try {
            // Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    " VALUES ("+systemManager.toString()+");";//"\'"+systemManager.getUserMail()+"\'"+","+"\'"+systemManager.getPassword()+"\'"+","+"\'"+systemManager.getName()+"\'"+","+"\'"+systemManager.getBirthDate().toString()+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void update(String userMail , SystemManager systemManager) {
        //delete and than add new one
        delete(userMail);
        save(systemManager);
    }

    @Override
    public void delete(String userMail) {
        try {
            //   Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM "+getTableName()+
                    "WHERE userName = "+"\'"+userMail+"\'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean exist(String leagueName) {

        try {
            // Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM "+getTableName()+
                    "WHERE userName ="+"\'"+leagueName+"\'";
            System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}