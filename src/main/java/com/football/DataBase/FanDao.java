package com.football.DataBase;

import com.football.Domain.Users.Fan;
import com.football.Domain.Users.SystemManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
@Repository
public class FanDao implements DAOTEMP<Fan> {



    private static final FanDao instance = new FanDao();

    //private constructor to avoid client applications to use constructor
    public static FanDao getInstance(){
        return instance;
    }
    DBConnector dbc ;
    Connection connection;

    @Override
    public String getTableName() {
        return " fan ";
    }

    private FanDao() {
        dbc= DBConnector.getInstance();
        connection=dbc.getConnection();
    }


    @Override
    public String get(String id) {
        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userName="+"\'"+id+"\'"+";";
            //  System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String updates = rs.getString("updates");

                toReturn = userName + ":" + EncryptPassword + ":" + name + ":" + birthDate + ":" + updates;
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
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From " + getTableName()+ ";";
            //    System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String updates=rs.getString("updates");

                String toReturn=userName+":"+EncryptPassword+":"+name+":"+birthDate+":"+updates;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }





    @Override
    public void save(Fan fan){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+fan.toString()+");";//"\'"+fan.getUserMail()+"\'"+","+"\'"+fan.getPassword()+"\'"+","+"\'"+fan.getName()+"\'"+","+"\'"+fan.getBirthDate().toString()+"\'"+","+"\'"+fan.getUpdates().toString()+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            //   System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , Fan fan) {
        //delete and than add new one
        delete(userMail);
        save(fan);
    }

    @Override
    public void delete(String userMail) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE userName ="+"\'"+userMail+"\'";
            // System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String fanName) {

        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE userName ="+"\'"+fanName+"\'";
            //   System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}