package com.football.DataBase;

import com.football.Domain.Users.Fan;
import com.football.Domain.Users.MainReferee;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
@Repository
public class MainRefereeDao implements DAOTEMP<MainReferee>{


    private static final MainRefereeDao instance = new MainRefereeDao();

    //private constructor to avoid client applications to use constructor
    public static MainRefereeDao getInstance(){
        return instance;
    }
    DBConnector dbc ;
    Connection connection;

    @Override
    public String getTableName() {
        return " mainReferee ";
    }

    private MainRefereeDao() {
        dbc= DBConnector.getInstance();
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
            //  System.out.println(sqlQuery);

            PreparedStatement ps = connection.prepareStatement(sqlQuery); //compiling query in the DB
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName=rs.getString("userName");
                String EncryptPassword=rs.getString("EncryptPassword");
                String name=rs.getString("name");
                String birthDate=rs.getString("birthDate");
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
    public void save(MainReferee mainReferee){
        try {
            Connection connection = dbc.getConnection();
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+mainReferee.toString()+");";// "\'"+mainReferee.getUserMail()+ "\'"+","+ "\'"+mainReferee.getPassword()+ "\'"+","+ "\'"+mainReferee.getName()+ "\'"+","+ "\'"+mainReferee.getBirthDate().toString()+ "\'"+","+ "\'"+mainReferee.getType()+ "\'"+","+ "\'"+mainReferee.getGameSchedule().toString()+ "\'"+");";
            //finish it
            // TODO: 12/05/2020
            //  System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , MainReferee mainReferee) {
        //delete and than add new one
        delete(userMail);
        save(mainReferee);
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
            //System.out.println(sqlQuery);
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}