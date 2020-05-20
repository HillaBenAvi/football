package com.football.DataBase;

import com.football.Domain.Users.Fan;
import com.football.Domain.Users.MainReferee;
import com.football.Domain.Users.SecondaryReferee;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class SecondaryRefereeDao implements DAOTEMP<SecondaryReferee>{

    private static final SecondaryRefereeDao instance = new SecondaryRefereeDao();

    //private constructor to avoid client applications to use constructor
    public static SecondaryRefereeDao getInstance(){
        return instance;
    }

    DBConnector dbc= DBConnector.getInstance();
    Connection connection;

    @Override
    public String getTableName() {
        return " secondaryReferee ";
    }

    private SecondaryRefereeDao() {

       connection=dbc.getConnection();
    }


    @Override
    public String get(String id) {
        String toReturn="";
        try {
            Connection connection = dbc.getConnection();
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userName="+ "\'"+id+ "\'"+";";
            //System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String training = rs.getString("training");
                String games = rs.getString("games");

                toReturn = userName + ":" + EncryptPassword + ":" + name + ":" + birthDate + ":" + training + ":" + games;
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
            //System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String EncryptPassword = rs.getString("EncryptPassword");
                String name = rs.getString("name");
                String birthDate = rs.getString("birthDate");
                String training=rs.getString("training");
                String games=rs.getString("games");


                String toReturn=userName+":"+EncryptPassword+":"+name+":"+birthDate+":"+training+":"+games;
                allTheTable.add(toReturn);
            }
            rs.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return allTheTable;
    }





    @Override
    public void save(SecondaryReferee secondaryReferee){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+secondaryReferee.toString()+");";// "\'" +secondaryReferee.getUserMail() + "\'" +","+  "\'" +secondaryReferee.getPassword() + "\'" +","+ "\'" +secondaryReferee.getName() + "\'" +","+ "\'" +secondaryReferee.getBirthDate().toString() + "\'" +","+ "\'" +secondaryReferee.getType() + "\'" +","+ "\'"+secondaryReferee.getGameSchedule().toString()+ "\'"+");";
            //finish it
            // TODO: 12/05/2020
            // System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , SecondaryReferee secondaryReferee) {
        //delete and than add new one
        delete(userMail);
        save(secondaryReferee);
    }

    @Override
    public void delete(String userMail) {
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE  userName ="+ "\'"+userMail+ "\'";
            //  System.out.println(sql);
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
                    "WHERE userName = "+ "\'"+fanName+ "\'";
            //  System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}