package com.football.DataBase;

import com.football.Domain.Asset.Field;
import com.football.Domain.Users.SecondaryReferee;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
public class FieldDao implements DAOTEMP<Field> {

    private static final FieldDao instance = new FieldDao();

    //private constructor to avoid client applications to use constructor
    public static FieldDao getInstance(){
        return instance;
    }
    DBConnector dbc;//= DBConnector.getInstance();
    Connection connection;

    @Override
    public String getTableName() {
        return " field ";
    }

    private FieldDao() {

        connection=dbc.getConnection();
    }

    @Override
    public String get(String id) {
        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE nameOfField="+"\'"+id+"\'"+";";
            //   System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String nameOfField = rs.getString("nameOfField");
                String team = rs.getString("team");


                toReturn = nameOfField + ":" + team;
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
            //  System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String nameOfField=rs.getString("nameOfField");
                String team=rs.getString("team");


                String toReturn=nameOfField+":"+team;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }





    @Override
    public void save(Field field){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+field.toString()+");";//"\'"+field.getNameOfField()+"\'"+","+"\'"+" "+"\'"+");";
            //finish it
            // TODO: 12/05/2020
            //     System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , Field field) {
        //delete and than add new one
        delete(userMail);
        save(field);
    }

    @Override
    public void delete(String nameOfField) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE  nameOfField="+"\'"+nameOfField+"\'";
            //     System.out.println(sql);
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
                    "WHERE nameOfField ="+"\'"+fanName+"\'";
            //   System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}