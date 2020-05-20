package com.football.DataBase;

import com.football.Domain.Asset.Manager;
import com.football.Domain.Users.Owner;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
public class OwnerDao implements DAOTEMP<Owner> {


    private static final OwnerDao instance = new OwnerDao();

    //private constructor to avoid client applications to use constructor
    public static OwnerDao getInstance(){
        return instance;
    }
    DBConnector dbc;//= DBConnector.getInstance();
    Connection connection;

    @Override
    public String getTableName() {
        return " owners ";
    }

    private OwnerDao() {

        connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {
        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userName="+"\'"+id+"\'"+";";
            //   System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();

            if(rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String teams = rs.getString("teams");

                toReturn = userName + ":" + EncryptPassword + ":" + name + ":" + birthDate + ":" + teams;
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
            //   System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String teams=rs.getString("teams");

                String toReturn=userName+":"+EncryptPassword+":"+name+":"+birthDate+":"+teams;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }

    @Override
    public void save(Owner owner){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+owner.toString()+");";//+"\'"+owner.getUserMail()+"\'"+","+"\'"+owner.getPassword()+"\'"+","+"\'"+owner.getName()+"\'"+","+"\'"+owner.getBirthDate().toString()+"\'"+","+"\'"+owner.getTeams().toString()+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            //  System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , Owner owner) {
        //delete and than add new one
        delete(userMail);
        save(owner);
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
    public boolean exist(String ownerName) {

        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE userName ="+"\'"+ownerName+"\'";
            //  System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}