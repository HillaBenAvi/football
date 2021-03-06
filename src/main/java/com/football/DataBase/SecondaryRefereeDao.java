package com.football.DataBase;

import com.football.Domain.Users.SecondaryReferee;
import com.football.Exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
@Repository

public class SecondaryRefereeDao implements DAO<SecondaryReferee> {

    @Autowired
    public DBConnector dbc=new DBConnector();;

  //  private static final SecondaryRefereeDao instance = new SecondaryRefereeDao();

    //private constructor to avoid client applications to use constructor
  //  public static SecondaryRefereeDao getInstance(){
   //     return instance;
  //  }

 //   DBConnector dbc;//= DBConnector.getInstance();
 Connection connection=dbc.getConnection();

    @Override
    public String getTableName() {
        return " secondaryReferee ";
    }

    public SecondaryRefereeDao() {

   //    connection=dbc.getConnection();
    }


    @Override
    public String get(String id) {
        String toReturn="";
        try {
            String sqlQuery = "SELECT * From "+getTableName()+" WHERE userName="+ "\'"+id+ "\'"+";";

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
            String sqlQuery = "SELECT * From " + getTableName()+ ";";

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
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO"+getTableName()+
                    "VALUES ("+secondaryReferee.toString()+");";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void update(String userMail , SecondaryReferee secondaryReferee) throws ObjectNotExist {
        if(exist(secondaryReferee.getUserMail())) {
            //delete and than add new one
            delete(userMail);
            save(secondaryReferee);
        }
        else
        {
            throw new ObjectNotExist("this object not exist , so you cant update it");
        }

    }

    @Override
    public void delete(String userMail) {
        try {
            Statement stmt = connection.createStatement();

            String sql = "DELETE FROM"+getTableName()+
                    "WHERE  userName ="+ "\'"+userMail+ "\'";
            stmt.executeUpdate(sql);
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public boolean exist(String fanName) {

        try {
            Statement stmt = connection.createStatement();

            String sqlQuery = "SELECT * FROM"+getTableName()+
                    "WHERE userName = "+ "\'"+fanName+ "\'";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs.next();

        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }
}